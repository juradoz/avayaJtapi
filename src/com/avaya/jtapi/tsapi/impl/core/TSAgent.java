package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.LucentAgentStateInfoEx;
import com.avaya.jtapi.tsapi.LucentV5AgentStateInfo;
import com.avaya.jtapi.tsapi.LucentV6AgentStateInfoEx;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentState;
import com.avaya.jtapi.tsapi.csta1.LucentSetAgentState;
import com.avaya.jtapi.tsapi.csta1.LucentV5SetAgentState;
import com.avaya.jtapi.tsapi.csta1.LucentV6SetAgentState;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import java.util.Vector;
import org.apache.log4j.Logger;

public final class TSAgent {
	private static Logger log = Logger.getLogger(TSAgent.class);
	private TSProviderImpl provider;
	TSDevice agentDevice;
	private int state;
	private int lucentWorkMode;
	private int workMode;
	private int reasonCode;
	private int pendingState;
	private int pendingReasonCode;
	private String agentID;
	private TSDevice acdDevice;
	private String passwd;
	private TSAgentKey agentKey;
	boolean constructed = false;
	private Vector<TSDevice> skillsVector;
	private int refCount = 0;
	private ParentAgent parentAgent;

	void dump(String indent) {
		log.trace(indent + "***** AGENT DUMP *****");
		log.trace(indent + "TSAgent: " + this);
		log.trace(indent + "TSAgent ID: " + this.agentID);
		log.trace(indent + "TSAgent key: " + this.agentKey);
		log.trace(indent + "TSAgent state: " + this.state);
		log.trace(indent + "TSAgent workMode: " + this.workMode);
		log.trace(indent + "TSAgent agentDevice: " + this.agentDevice);
		log.trace(indent + "TSAgent acdDevice: " + this.acdDevice);
		log.trace(indent + "***** AGENT DUMP END *****");
	}

	public TSProviderImpl getTSProviderImpl() {
		return this.provider;
	}

	Vector<TsapiTerminalMonitor> getTerminalObservers() {
		return this.agentDevice.getTerminalObservers();
	}

	void updateState(int _state, int _workMode, int _reasonCode,
			Vector<TSEvent> eventList) {
		updateState(_state, _workMode, _reasonCode, 0, 0, eventList);
	}

	void updateState(int _state, int _workMode, int _reasonCode,
			int _pendingState, int _pendingReasonCode, Vector<TSEvent> eventList) {
		updateState(_state, _workMode, _reasonCode, _pendingState,
				_pendingReasonCode, -1, eventList);
	}

	void updateState(int _state, int _workMode, int _reasonCode,
			int _pendingState, int _pendingReasonCode, int _lucentworkmode,
			Vector<TSEvent> eventList) {
		boolean stateChange = false;
		synchronized (this) {
			if (((this.state == _state) && (this.workMode == _workMode)
					&& (this.reasonCode == _reasonCode)
					&& (this.pendingState == _pendingState)
					&& (this.pendingReasonCode == _pendingReasonCode) && (this.lucentWorkMode == _lucentworkmode))
					|| (this.state == 2)) {
				return;
			}

			if (this.state != _state) {
				stateChange = true;
			}
			this.state = _state;
			this.workMode = _workMode;
		}

		this.reasonCode = _reasonCode;
		this.pendingState = _pendingState;
		this.pendingReasonCode = _pendingReasonCode;
		this.lucentWorkMode = _lucentworkmode;

		if (this.parentAgent == null) {
			updateParentAgent();
		}

		if (stateChange) {
			Vector<TSEvent> localEventList = new Vector<TSEvent>();

			getEvent(this.state, localEventList);
			if (eventList == null) {
				if (localEventList.size() > 0) {
					Vector<?> observers = null;
					if (this.acdDevice != null) {
						observers = this.acdDevice.getAddressObservers();
						for (int j = 0; j < observers.size(); j++) {
							TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
									.elementAt(j);
							callback.deliverEvents(localEventList, false);
						}

					} else {
						for (int i = 0; i < this.skillsVector.size(); i++) {
							TSDevice skillDevice = (TSDevice) this.skillsVector
									.elementAt(i);
							observers = skillDevice.getAddressObservers();
							for (int j = 0; j < localEventList.size(); j++) {
								TSEvent ev = (TSEvent) localEventList
										.elementAt(j);
								Object tsTarget = ev.getEventTarget();
								if ((tsTarget instanceof TSAgent)) {
									ev.setSkillDevice(skillDevice);
								}
							}
							for (int j = 0; j < observers.size(); j++) {
								TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
										.elementAt(j);
								callback.deliverEvents(localEventList, false);
							}
						}
					}

					Vector<?> tObservers = getTerminalObservers();
					for (int j = 0; j < tObservers.size(); j++) {
						TsapiTerminalMonitor callback = (TsapiTerminalMonitor) tObservers
								.elementAt(j);
						callback.deliverEvents(localEventList, false);
					}
				}

				this.parentAgent.deliverEventsToOtherSkills(this,
						localEventList, this.acdDevice);
			} else {
				for (int i = 0; i < localEventList.size(); i++) {
					eventList.addElement(localEventList.elementAt(i));
				}
			}
		}

		if (this.state == 2) {
			this.agentDevice.removeFromAgentTermVector(this);
			if (this.acdDevice != null) {
				this.acdDevice.removeFromACDVector(this);
			} else {
				for (int i = 0; i < this.skillsVector.size(); i++) {
					((TSDevice) this.skillsVector.elementAt(i))
							.removeFromACDVector(this);
				}
			}
			delete();
		}
	}

	void getEvent(int state, Vector<TSEvent> localEventList) {
		switch (state) {
		case 1:
			localEventList.addElement(new TSEvent(40, this));
			localEventList.addElement(new TSEvent(47, this));
			break;
		case 2:
			localEventList.addElement(new TSEvent(41, this));
			localEventList.addElement(new TSEvent(48, this));
			break;
		case 4:
			localEventList.addElement(new TSEvent(42, this));
			localEventList.addElement(new TSEvent(49, this));
			break;
		case 3:
			localEventList.addElement(new TSEvent(43, this));
			localEventList.addElement(new TSEvent(50, this));
			break;
		case 6:
			localEventList.addElement(new TSEvent(44, this));
			localEventList.addElement(new TSEvent(51, this));
			break;
		case 5:
			localEventList.addElement(new TSEvent(45, this));
			localEventList.addElement(new TSEvent(52, this));
			break;
		case 7:
			localEventList.addElement(new TSEvent(46, this));
			localEventList.addElement(new TSEvent(53, this));
			break;
		}
	}

	private LucentSetAgentState createLucentSetAgentState(int workMode,
			int reasonCode, boolean enablePending) {
		if (this.provider.isLucentV6())
			return new LucentV6SetAgentState((short) workMode, reasonCode,
					enablePending);
		if (this.provider.isLucentV5()) {
			return new LucentV5SetAgentState((short) workMode, reasonCode);
		}
		return new LucentSetAgentState((short) workMode);
	}

	private LucentSetAgentState createLucentSetAgentState(int workMode,
			int reasonCode) {
		return createLucentSetAgentState(workMode, reasonCode, false);
	}

	private LucentSetAgentState createLucentSetAgentState(int workMode) {
		return createLucentSetAgentState(workMode, 0, false);
	}

	void setState(int _state, int _workMode, int _reasonCode)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		setState(_state, _workMode, _reasonCode, false);
	}

	public boolean setState(int _state, int _workMode, int _reasonCode,
			boolean _enablePending) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException {
		int effectivePendingReasonCode = 0;

		if (((this.state == _state) && (this.workMode == _workMode) && (this.reasonCode == _reasonCode))
				|| (this.state == 2)) {
			return false;
		}

		if ((_state != 1) && (_state != 2) && (_state != 4) && (_state != 3)
				&& (_state != 6) && (_state != 5)) {
			throw new TsapiInvalidArgumentException(3, 0, "state not valid");
		}

		CSTAPrivate reqPriv = null;
		if (this.provider.isLucent()) {
			LucentSetAgentState lsas = null;

			if (_state == 3) {
				lsas = createLucentSetAgentState(-1, _reasonCode,
						_enablePending);

				effectivePendingReasonCode = _reasonCode;
			}
			if ((_state == 4) && (_workMode != 0)) {
				if (_workMode == 1)
					lsas = createLucentSetAgentState(3);
				else if (_workMode == 2)
					lsas = createLucentSetAgentState(4);
			}
			if (_state == 5) {
				lsas = createLucentSetAgentState(-1, 0, _enablePending);
			}
			if ((_state == 2) && (this.provider.isLucentV5())) {
				lsas = createLucentSetAgentState(-1, _reasonCode);
			}
			if (lsas != null) {
				reqPriv = lsas.makeTsapiPrivate();
			}
		}
		if (this.acdDevice != null)
			this.agentDevice.setTSAgent(this.acdDevice.getName(), _state,
					this.agentID, this.passwd, reqPriv);
		else {
			this.agentDevice.setTSAgent(null, _state, this.agentID,
					this.passwd, reqPriv);
		}

		if ((_state == 2) && (monitorIsSet())) {
			return this.agentDevice.wereChangesHeldPending();
		}

		if (this.agentDevice.wereChangesHeldPending()) {
			updateState(this.state, this.workMode, this.reasonCode, _state,
					effectivePendingReasonCode, null);
		} else {
			updateState(_state, _workMode, _reasonCode, null);
		}

		return this.agentDevice.wereChangesHeldPending();
	}

	int getInternalState() {
		return this.state;
	}

	void copyAgentState(TSAgent agent) {
		this.state = agent.state;
		this.workMode = agent.workMode;
		this.lucentWorkMode = agent.lucentWorkMode;
		this.reasonCode = agent.reasonCode;
		this.pendingState = agent.pendingState;
		this.pendingReasonCode = agent.pendingReasonCode;
	}

	void updateAgentState() {
		this.state = this.parentAgent.getState();
		this.workMode = this.parentAgent.getWorkMode();
		this.lucentWorkMode = this.parentAgent.getLucentWorkMode();
		this.reasonCode = this.parentAgent.getReasonCode();
		this.pendingState = this.parentAgent.getPendingState();
		this.pendingReasonCode = this.parentAgent.getPendingReasonCode();
	}

	public int getState() {
		if ((this.state == 2)
				|| ((monitorIsSet()) && (this.provider.getCapabilities()
						.getReadyEvent() != 0))) {
			return this.state;
		}
		if (this.provider.getCapabilities().getQueryAgentState() != 0) {
			CSTAPrivate priv = null;

			if ((this.provider.isLucent()) && (this.acdDevice != null)) {
				LucentQueryAgentState lqas = new LucentQueryAgentState(
						this.acdDevice.getName());
				priv = lqas.makeTsapiPrivate();
			}

			ConfHandler handler = new QueryAgentStateConfHandler(this);
			try {
				this.provider.tsapi.queryAgentState(this.agentDevice.getName(),
						priv, handler);
			} catch (TsapiPlatformException e) {
				throw e;
			} catch (Exception e) {
				if ((e instanceof ITsapiException)) {
					throw new TsapiPlatformException(
							((ITsapiException) e).getErrorType(),
							((ITsapiException) e).getErrorCode(),
							"queryAgentState failure");
				}
				throw new TsapiPlatformException(4, 0,
						"queryAgentState failure");
			}
			return this.state;
		}

		return this.state;
	}

	boolean monitorIsSet() {
		if (this.acdDevice != null) {
			if (this.acdDevice.getAddressObservers().size() > 0) {
				return true;
			}
		} else {
			for (int i = 0; i < this.skillsVector.size(); i++) {
				if (((TSDevice) this.skillsVector.elementAt(i))
						.getAddressObservers().size() > 0)
					return true;
			}
		}
		if (this.agentDevice.getTerminalObservers().size() > 0)
			return true;
		return false;
	}

	void getSnapshot(Vector<TSEvent> eventList, boolean isTerminalSnapshot) {
		if (eventList == null) {
			return;
		}
		updateAgentState();
		if (!isTerminalSnapshot)
			getEvent(this.state, eventList);
	}

	public LucentAgentStateInfoEx getStateInfo() {
		getState();

		if (this.provider.isLucentV5()) {
			if (this.provider.isLucentV6()) {
				return new LucentV6AgentStateInfoEx(this.state, this.workMode,
						this.reasonCode, this.pendingState,
						this.pendingReasonCode, this.lucentWorkMode);
			}
			return new LucentV5AgentStateInfo(this.state, this.workMode,
					this.reasonCode);
		}

		return new LucentAgentStateInfoEx(this.state, this.workMode);
	}

	public TSDevice getTSAgentDevice() {
		return this.agentDevice;
	}

	public TSDevice getTSACDDevice() {
		return this.acdDevice;
	}

	public String getAgentID() {
		return this.agentID;
	}

	TSAgentKey getAgentKey() {
		return this.agentKey;
	}

	TSAgent(TSProviderImpl _provider, TSAgentKey _agentKey, String _passwd) {
		this.constructed = false;
		this.provider = _provider;
		this.passwd = _passwd;
		this.agentKey = _agentKey;
		this.lucentWorkMode = -1;
		this.state = 0;
		this.workMode = 0;
		this.reasonCode = 0;
		this.pendingState = 0;
		this.pendingReasonCode = 0;

		log.info("constructing TSAgent with agentKey=" + this.agentKey
				+ " for " + this.provider);
	}

	synchronized void finishConstruction() {
		this.agentDevice = this.provider
				.createDevice(this.agentKey.agentDeviceID);
		if (this.agentKey.acdDeviceID != null) {
			this.acdDevice = this.provider
					.createDevice(this.agentKey.acdDeviceID);
			this.acdDevice.addToACDVector(this);
		}
		if (this.agentKey.agentID != null)
			this.agentID = this.agentKey.agentID;
		this.agentDevice.addToAgentTermVector(this);
		this.skillsVector = new Vector<TSDevice>();
		this.constructed = true;

		if (this.parentAgent == null) {
			updateParentAgent();
		}
		notifyAll();
	}

	synchronized void waitForConstruction() {
		if (!this.constructed) {
			try {
				wait(TSProviderImpl.DEFAULT_TIMEOUT);
			} catch (InterruptedException e) {
			}
			if (!this.constructed) {
				throw new TsapiPlatformException(4, 0,
						"could not finish agent construction");
			}
		}
	}

	TSDevice getACDDeviceID() {
		return this.acdDevice;
	}

	void addToSkillsVector(String acdDeviceID) {
		TSDevice skillDevice = this.provider.createDevice(acdDeviceID);
		if (!this.skillsVector.contains(skillDevice)) {
			sendAgentSnapshot(skillDevice);
			this.skillsVector.addElement(this.provider
					.createDevice(acdDeviceID));
			skillDevice.addToACDVector(this);
		}
	}

	Vector<TSDevice> getSkillsVector() {
		return this.skillsVector;
	}

	void sendAgentSnapshot(TSDevice skillDevice) {
		Vector<TSEvent> localEventList = new Vector<TSEvent>();
		getEvent(this.state, localEventList);

		for (int i = 0; i < localEventList.size(); i++) {
			TSEvent ev = (TSEvent) localEventList.elementAt(i);
			ev.setSkillDevice(skillDevice);
		}
		Vector<?> observers = skillDevice.getAddressObservers();

		for (int j = 0; j < observers.size(); j++) {
			TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
					.elementAt(j);
			callback.deliverEvents(localEventList, true);
		}
	}

	synchronized void delete() {
		log.info("Agent object=" + this + "being deleted" + " for "
				+ this.provider);

		if (this.agentKey != null) {
			this.provider.deleteAgentFromHash(this.agentKey);
			this.provider.addAgentToSaveHash(this);
		}
	}

	public String toString() {
		return "TSAgent" + getMyCustomString() + "@"
				+ Integer.toHexString(hashCode());
	}

	private String getMyCustomString() {
		return "[" + this.agentKey + "]";
	}

	public void referenced() {
		this.refCount += 1;
	}

	public void unreferenced() {
		if (isReferenced()) {
			this.refCount -= 1;
		}

		if (!isReferenced()) {
			if (this.agentDevice != null) {
				this.agentDevice.testDelete();
			}
		}
	}

	boolean isReferenced() {
		return this.refCount > 0;
	}

	void updateParentAgent() {
		if (this.parentAgent == null) {
			TSAgentKey parentAgentKey = new TSAgentKey(
					this.agentKey.agentDeviceID, null, this.agentKey.agentID);
			log.info("ParentAgent Object " + parentAgentKey
					+ " being created for provider " + this.provider);
			this.parentAgent = this.provider.createParentAgent(parentAgentKey);
			this.parentAgent.addSkill(this.acdDevice);
		}
	}

	public ParentAgent getParentAgent() {
		return this.parentAgent;
	}

	public int getLucentWorkMode() {
		return this.lucentWorkMode;
	}

	public int getWorkMode() {
		return this.workMode;
	}

	public int getReasonCode() {
		return this.reasonCode;
	}

	public int getPendingState() {
		return this.pendingState;
	}

	public int getPendingReasonCode() {
		return this.pendingReasonCode;
	}
}