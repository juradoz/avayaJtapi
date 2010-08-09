package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import org.apache.log4j.Logger;

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

	TSAgent(TSProviderImpl _provider, TSAgentKey _agentKey, String _passwd) {
		constructed = false;
		provider = _provider;
		passwd = _passwd;
		agentKey = _agentKey;
		lucentWorkMode = -1;
		state = 0;
		workMode = 0;
		reasonCode = 0;
		pendingState = 0;
		pendingReasonCode = 0;

		log.info("constructing TSAgent with agentKey=" + agentKey + " for "
				+ provider);
	}

	void addToSkillsVector(String acdDeviceID) {
		TSDevice skillDevice = provider.createDevice(acdDeviceID);
		if (skillsVector.contains(skillDevice)) {
			return;
		}
		sendAgentSnapshot(skillDevice);
		skillsVector.addElement(provider.createDevice(acdDeviceID));
		skillDevice.addToACDVector(this);
	}

	private LucentSetAgentState createLucentSetAgentState(int workMode) {
		return createLucentSetAgentState(workMode, 0, false);
	}

	private LucentSetAgentState createLucentSetAgentState(int workMode,
			int reasonCode) {
		return createLucentSetAgentState(workMode, reasonCode, false);
	}

	private LucentSetAgentState createLucentSetAgentState(int workMode,
			int reasonCode, boolean enablePending) {
		if (provider.isLucentV6()) {
			return new LucentV6SetAgentState((short) workMode, reasonCode,
					enablePending);
		}
		if (provider.isLucentV5()) {
			return new LucentV5SetAgentState((short) workMode, reasonCode);
		}
		return new LucentSetAgentState((short) workMode);
	}

	synchronized void delete() {
		log.info("Agent object=" + this + "being deleted" + " for " + provider);

		if (agentKey == null) {
			return;
		}
		provider.deleteAgentFromHash(agentKey);
		provider.addAgentToSaveHash(this);
	}

	void dump(String indent) {
		log.trace(indent + "***** AGENT DUMP *****");
		log.trace(indent + "TSAgent: " + this);
		log.trace(indent + "TSAgent ID: " + agentID);
		log.trace(indent + "TSAgent key: " + agentKey);
		log.trace(indent + "TSAgent state: " + state);
		log.trace(indent + "TSAgent workMode: " + workMode);
		log.trace(indent + "TSAgent agentDevice: " + agentDevice);
		log.trace(indent + "TSAgent acdDevice: " + acdDevice);
		log.trace(indent + "***** AGENT DUMP END *****");
	}

	synchronized void finishConstruction() {
		agentDevice = provider.createDevice(agentKey.agentDeviceID);
		if (agentKey.acdDeviceID != null) {
			acdDevice = provider.createDevice(agentKey.acdDeviceID);
			acdDevice.addToACDVector(this);
		}
		if (agentKey.agentID != null) {
			agentID = agentKey.agentID;
		}
		agentDevice.addToAgentTermVector(this);
		skillsVector = new Vector();
		constructed = true;
		super.notifyAll();
	}

	TSDevice getACDDeviceID() {
		return acdDevice;
	}

	public String getAgentID() {
		return agentID;
	}

	TSAgentKey getAgentKey() {
		return agentKey;
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
		}
	}

	int getInternalState() {
		return state;
	}

	private String getMyCustomString() {
		return "[" + agentKey + "]";
	}

	Vector<TSDevice> getSkillsVector() {
		return skillsVector;
	}

	void getSnapshot(Vector<TSEvent> eventList) {
		if (eventList == null) {
			return;
		}
		getEvent(state, eventList);
	}

	public int getState() {
		if ((state == 2)
				|| ((monitorIsSet()) && (provider.getCapabilities()
						.getReadyEvent() != 0))) {
			return state;
		}
		if (provider.getCapabilities().getQueryAgentState() != 0) {
			CSTAPrivate priv = null;

			if ((provider.isLucent()) && (acdDevice != null)) {
				LucentQueryAgentState lqas = new LucentQueryAgentState(
						acdDevice.getName());
				priv = lqas.makeTsapiPrivate();
			}

			ConfHandler handler = new QueryAgentStateConfHandler(this);
			try {
				provider.tsapi.queryAgentState(agentDevice.getName(), priv,
						handler);
			} catch (TsapiPlatformException e) {
				throw e;
			} catch (Exception e) {
				if (e instanceof ITsapiException) {
					throw new TsapiPlatformException(((ITsapiException) e)
							.getErrorType(), ((ITsapiException) e)
							.getErrorCode(), "queryAgentState failure");
				}
				throw new TsapiPlatformException(4, 0,
						"queryAgentState failure");
			}
			return state;
		}

		return state;
	}

	public LucentAgentStateInfoEx getStateInfo() {
		getState();

		if (provider.isLucentV5()) {
			if (provider.isLucentV6()) {
				return new LucentV6AgentStateInfoEx(state, workMode,
						reasonCode, pendingState, pendingReasonCode,
						lucentWorkMode);
			}
			return new LucentV5AgentStateInfo(state, workMode, reasonCode);
		}

		return new LucentAgentStateInfoEx(state, workMode);
	}

	Vector<TsapiTerminalMonitor> getTerminalObservers() {
		return agentDevice.getTerminalObservers();
	}

	public TSDevice getTSACDDevice() {
		return acdDevice;
	}

	public TSDevice getTSAgentDevice() {
		return agentDevice;
	}

	public TSProviderImpl getTSProviderImpl() {
		return provider;
	}

	boolean isReferenced() {
		return refCount > 0;
	}

	boolean monitorIsSet() {
		if (acdDevice != null) {
			if (acdDevice.getAddressObservers().size() > 0) {
				return true;
			}
		} else {
			for (int i = 0; i < skillsVector.size(); ++i) {
				if ((skillsVector.elementAt(i)).getAddressObservers().size() > 0) {
					return true;
				}
			}
		}
		return agentDevice.getTerminalObservers().size() > 0;
	}

	public void referenced() {
		refCount += 1;
	}

	void sendAgentSnapshot(TSDevice skillDevice) {
		Vector localEventList = new Vector();
		getEvent(state, localEventList);

		for (int i = 0; i < localEventList.size(); ++i) {
			TSEvent ev = (TSEvent) localEventList.elementAt(i);
			ev.setSkillDevice(skillDevice);
		}
		Vector observers = skillDevice.getAddressObservers();

		for (int j = 0; j < observers.size(); ++j) {
			TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
					.elementAt(j);
			callback.deliverEvents(localEventList, true);
		}
	}

	void setState(int _state, int _workMode, int _reasonCode)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		setState(_state, _workMode, _reasonCode, false);
	}

	public boolean setState(int _state, int _workMode, int _reasonCode,
			boolean _enablePending) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException {
		int effectivePendingReasonCode = 0;

		if (((state == _state) && (workMode == _workMode) && (reasonCode == _reasonCode))
				|| (state == 2)) {
			return false;
		}

		if ((_state != 1) && (_state != 2) && (_state != 4) && (_state != 3)
				&& (_state != 6) && (_state != 5)) {
			throw new TsapiInvalidArgumentException(3, 0, "state not valid");
		}

		CSTAPrivate reqPriv = null;
		if (provider.isLucent()) {
			LucentSetAgentState lsas = null;

			if (_state == 3) {
				lsas = createLucentSetAgentState(-1, _reasonCode,
						_enablePending);

				effectivePendingReasonCode = _reasonCode;
			}
			if ((_state == 4) && (_workMode != 0)) {
				if (_workMode == 1) {
					lsas = createLucentSetAgentState(3);
				} else if (_workMode == 2) {
					lsas = createLucentSetAgentState(4);
				}
			}
			if (_state == 5) {
				lsas = createLucentSetAgentState(-1, 0, _enablePending);
			}
			if ((_state == 2) && (provider.isLucentV5())) {
				lsas = createLucentSetAgentState(-1, _reasonCode);
			}
			if (lsas != null) {
				reqPriv = lsas.makeTsapiPrivate();
			}
		}
		if (acdDevice != null) {
			agentDevice.setTSAgent(acdDevice.getName(), _state, agentID,
					passwd, reqPriv);
		} else {
			agentDevice.setTSAgent(null, _state, agentID, passwd, reqPriv);
		}
		if (agentDevice.wereChangesHeldPending()) {
			updateState(state, workMode, reasonCode, _state,
					effectivePendingReasonCode, null);
		} else {
			updateState(_state, _workMode, _reasonCode, null);
		}

		return agentDevice.wereChangesHeldPending();
	}

	@Override
	public String toString() {
		return "TSAgent" + getMyCustomString() + "@"
				+ Integer.toHexString(super.hashCode());
	}

	public void unreferenced() {
		if (isReferenced()) {
			refCount -= 1;
		}

		if ((isReferenced()) || (agentDevice == null)) {
			return;
		}
		agentDevice.testDelete();
	}

	void updateState(int _state, int _workMode, int _reasonCode,
			int _pendingState, int _pendingReasonCode, int _lucentworkmode,
			Vector<TSEvent> eventList) {
		boolean stateChange = false;
		synchronized (this) {
			if (((state == _state) && (workMode == _workMode)
					&& (reasonCode == _reasonCode)
					&& (pendingState == _pendingState)
					&& (pendingReasonCode == _pendingReasonCode) && (lucentWorkMode == _lucentworkmode))
					|| (state == 2)) {
				return;
			}

			if (state != _state) {
				stateChange = true;
			}
			state = _state;
			workMode = _workMode;
		}

		reasonCode = _reasonCode;
		pendingState = _pendingState;
		pendingReasonCode = _pendingReasonCode;
		lucentWorkMode = _lucentworkmode;

		if (stateChange) {
			Vector localEventList = new Vector();

			getEvent(state, localEventList);
			if (eventList == null) {
				if (localEventList.size() > 0) {
					Vector observers = null;
					if (acdDevice != null) {
						observers = acdDevice.getAddressObservers();
						for (int j = 0; j < observers.size(); ++j) {
							TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
									.elementAt(j);
							callback.deliverEvents(localEventList, false);
						}

					} else {
						for (int i = 0; i < skillsVector.size(); ++i) {
							TSDevice skillDevice = skillsVector.elementAt(i);
							observers = skillDevice.getAddressObservers();
							for (int j = 0; j < localEventList.size(); ++j) {
								TSEvent ev = (TSEvent) localEventList
										.elementAt(j);
								Object tsTarget = ev.getEventTarget();
								if (!(tsTarget instanceof TSAgent)) {
									continue;
								}
								ev.setSkillDevice(skillDevice);
							}

							for (int j = 0; j < observers.size(); ++j) {
								TsapiAddressMonitor callback = (TsapiAddressMonitor) observers
										.elementAt(j);
								callback.deliverEvents(localEventList, false);
							}
						}
					}

					Vector tObservers = getTerminalObservers();
					for (int j = 0; j < tObservers.size(); ++j) {
						TsapiTerminalMonitor callback = (TsapiTerminalMonitor) tObservers
								.elementAt(j);
						callback.deliverEvents(localEventList, false);
					}
				}
			} else {
				int i;
				for (i = 0; i < localEventList.size(); ++i) {
					eventList.addElement((TSEvent) localEventList.elementAt(i));
				}
			}
		}

		if (state != 2) {
			return;
		}
		agentDevice.removeFromAgentTermVector(this);
		if (acdDevice != null) {
			acdDevice.removeFromACDVector(this);
		} else {
			for (int i = 0; i < skillsVector.size(); ++i) {
				(skillsVector.elementAt(i)).removeFromACDVector(this);
			}
		}
		delete();
	}

	void updateState(int _state, int _workMode, int _reasonCode,
			int _pendingState, int _pendingReasonCode, Vector<TSEvent> eventList) {
		updateState(_state, _workMode, _reasonCode, _pendingState,
				_pendingReasonCode, -1, eventList);
	}

	void updateState(int _state, int _workMode, int _reasonCode,
			Vector<TSEvent> eventList) {
		updateState(_state, _workMode, _reasonCode, 0, 0, eventList);
	}

	synchronized void waitForConstruction() {
		if (constructed) {
			return;
		}
		try {
			super.wait(TSProviderImpl.DEFAULT_TIMEOUT);
		} catch (InterruptedException e) {
		}
		if (constructed) {
			return;
		}
		throw new TsapiPlatformException(4, 0,
				"could not finish agent construction");
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSAgent JD-Core Version: 0.5.4
 */