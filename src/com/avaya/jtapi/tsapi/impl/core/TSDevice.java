package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.TsapiUnableToSendException;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAMonitorFilter;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplit;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplitConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLogin;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginResp;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentState;
import com.avaya.jtapi.tsapi.csta1.LucentQueryCallClassifier;
import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceName;
import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceNameConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentSetAgentState;
import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
import com.avaya.jtapi.tsapi.csta1.LucentV5SetAgentState;
import com.avaya.jtapi.tsapi.impl.TsapiAddressCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiCallControlForwarding;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.TsapiTerminalCapabilities;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiRouteMonitor;
import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.Tsapi;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.telephony.callcontrol.CallControlForwarding;
import javax.telephony.capabilities.TerminalCapabilities;
import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public final class TSDevice implements IDomainDevice {
	private static Logger log = Logger.getLogger(TSDevice.class);

	static int g_CreationCnt = 0;
	static int g_RefCnt = 0;
	private boolean wasReferenced = false;
	public static final short STATION = 0;
	public static final short VDN = 1;
	public static final short ACD = 2;
	Object obsSync;
	TSDeviceState curState;
	TSProviderImpl provider;
	int refCount = 0;
	private final Vector<CSTAExtendedDeviceID> devNameVector;
	int monitorDeviceXRefID = 0;
	int monitorCallsViaDeviceXRefID = 0;

	private boolean monitorPredictiveCallsViaDevice = false;
	private final Vector<TSConnection> connections;
	private final Vector<TSConnection> terminalConnections;
	private final Vector<TSCall> internalDeviceMonitors;
	private final Vector<TsapiTerminalMonitor> terminalMonitorThreads;
	private final Vector<TsapiAddressMonitor> addressMonitorThreads;
	private final Vector<TSCall> callsWaitingForConnectPostConditions;
	private final Vector<TsapiCallMonitor> callsAtAddressMonitorThreads;
	final Vector<TsapiCallMonitor> callsViaAddressMonitorThreads;
	private final Vector<TsapiCallMonitor> callsAtTerminalMonitorThreads;
	TsapiRouteMonitor tsRouteCallback = null;
	int registerReqID;
	public Hashtable<Integer, TSRouteSession> sessionHash;
	int numQueued;
	private final Vector<TSAgent> tsAgentTermVector;
	private final Vector<TSAgent> tsACDVector;
	public boolean dndInitialized;
	public boolean mwiInitialized;
	public boolean forwardingInitialized;
	public boolean dndState;
	public int msgWaitingBits;
	private final Vector<TsapiCallControlForwarding> fwdVector;
	private boolean isATerminal;
	private boolean isExternal;
	Object replyAddrPriv = null;
	Object replyTermPriv = null;
	private short deviceType;
	boolean waitingForAgents = false;

	private boolean asyncInitializationComplete = false;
	boolean changesWereHeldPending;
	private String associatedDevice = null;
	private short associatedClass;

	void dump(String indent) {
		log.trace(indent + "***** DEVICE DUMP *****");
		log.trace(indent + "TSDevice: " + this);
		log.trace(indent + "TSDevice names: ");
		synchronized (this.devNameVector) {
			for (int i = 0; i < this.devNameVector.size(); i++) {
				log.trace(indent + this.devNameVector.elementAt(i));
			}
		}

		log.trace(indent + "TSDevice connections: ");
		Vector<TSConnection> connectionsClone = (Vector<TSConnection>) this.connections.clone();

		for (int i = 0; i < connectionsClone.size(); i++) {
			TSConnection conn = (TSConnection) connectionsClone.elementAt(i);
			conn.dump(indent + " ");
		}

		log.trace(indent + "TSDevice terminalConnections: ");
		Vector<TSConnection> terminalConnectionsClone = (Vector<TSConnection>) this.terminalConnections
				.clone();
		TSConnection conn = null;
		for (int i = 0; i < terminalConnectionsClone.size(); i++) {
			conn = (TSConnection) terminalConnectionsClone.elementAt(i);
			conn.dump(indent + " ");
		}

		log.trace(indent + "TSDevice ACD Agents: ");
		Vector<TSAgent> tsACDVectorClone = (Vector<TSAgent>) this.tsACDVector.clone();

		for (int i = 0; i < tsACDVectorClone.size(); i++) {
			TSAgent agent = (TSAgent) tsACDVectorClone.elementAt(i);
			agent.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Terminal Agents: ");
		synchronized (this.tsAgentTermVector) {
			TSAgent agent = null;
			for (int i = 0; i < this.tsAgentTermVector.size(); i++) {
				agent = (TSAgent) this.tsAgentTermVector.elementAt(i);
				agent.dump(indent + " ");
			}
		}
		if (this.sessionHash != null) {
			log.trace(indent + "TSDevice Route Sessions: ");
			synchronized (this.sessionHash) {
				Enumeration<TSRouteSession> sessionEnum = this.sessionHash.elements();

				while (sessionEnum.hasMoreElements()) {
					TSRouteSession routeSession;
					try {
						routeSession = (TSRouteSession) sessionEnum
								.nextElement();
						routeSession.dump(indent + " ");
					} catch (NoSuchElementException e) {
						log.error(e.getMessage(), e);
					}
					continue;

				}
			}
		}
		log.trace(indent + "TSDevice Terminal Monitor Threads: ");

		Vector<TsapiTerminalMonitor> terminalMonitorThreadsClone = (Vector<TsapiTerminalMonitor>) this.terminalMonitorThreads
				.clone();

		for (int i = 0; i < terminalMonitorThreadsClone.size(); i++) {
			TsapiTerminalMonitor tOThreads = (TsapiTerminalMonitor) terminalMonitorThreadsClone
					.elementAt(i);
			tOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Address Monitor Threads: ");

		Vector<TsapiAddressMonitor> addressMonitorThreadsClone = (Vector<TsapiAddressMonitor>) this.addressMonitorThreads
				.clone();

		for (int i = 0; i < addressMonitorThreadsClone.size(); i++) {
			TsapiAddressMonitor aOThreads = (TsapiAddressMonitor) addressMonitorThreadsClone
					.elementAt(i);
			aOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Calls At Address Monitor Threads: ");
		Vector<TsapiCallMonitor> callsAtAddressMonitorThreadsClone = (Vector<TsapiCallMonitor>) this.callsAtAddressMonitorThreads
				.clone();

		for (int i = 0; i < callsAtAddressMonitorThreadsClone.size(); i++) {
			TsapiCallMonitor cAAOThreads = (TsapiCallMonitor) callsAtAddressMonitorThreadsClone
					.elementAt(i);
			cAAOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Calls Via Address Monitor Threads: ");
		Vector<TsapiCallMonitor> callsViaAddressMonitorThreadsClone = (Vector<TsapiCallMonitor>) this.callsViaAddressMonitorThreads
				.clone();

		for (int i = 0; i < callsViaAddressMonitorThreadsClone.size(); i++) {
			TsapiCallMonitor cVAOThreads = (TsapiCallMonitor) callsViaAddressMonitorThreadsClone
					.elementAt(i);
			cVAOThreads.dump(indent + " ");
		}

		log.trace(indent + "TSDevice Calls At Terminal Monitor Threads: ");
		Vector<TsapiCallMonitor> callsAtTerminalMonitorThreadsClone = (Vector<TsapiCallMonitor>) this.callsAtTerminalMonitorThreads
				.clone();

		for (int i = 0; i < callsAtTerminalMonitorThreadsClone.size(); i++) {
			TsapiCallMonitor cATOThreads = (TsapiCallMonitor) callsAtTerminalMonitorThreadsClone
					.elementAt(i);
			cATOThreads.dump(indent + " ");
		}
		log.trace(indent + "wasReferenced is " + this.wasReferenced);
		log.trace(indent + "***** DEVICE DUMP END *****");
	}

	synchronized void recreate() {
		this.curState.recreate(this);
	}

	synchronized void internalRecreate() {
		Vector<CSTAExtendedDeviceID> keys = new Vector<CSTAExtendedDeviceID>(this.devNameVector);
		log.info("Recreating deleted device " + this);
		for (int i = 0; i < keys.size(); i++) {
			String key = ((CSTAExtendedDeviceID) keys.elementAt(i))
					.getDeviceID();
			this.provider.addDeviceToHash(key, this);
		}

		setState(new TSDeviceStateActive());

		log.info("Device "
				+ ((CSTAExtendedDeviceID) this.devNameVector.lastElement())
						.getDeviceID() + " (object= " + this
				+ ") being re-added" + " for " + this.provider);
	}

	public String getDirectoryName() {
		recreate();
		try {
			LucentQueryDeviceName lqdn = new LucentQueryDeviceName(getName());
			Object lqdnConf = this.provider.sendPrivateData(
					lqdn.makeTsapiPrivate(), null, true);
			if ((lqdnConf instanceof LucentQueryDeviceNameConfEvent)) {
				return ((LucentQueryDeviceNameConfEvent) lqdnConf).getName();
			}
			return null;
		} catch (TsapiPlatformException e) {
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 12)) {
				return null;
			}

			throw e;
		} catch (Exception e) {
			if ((e instanceof TsapiInvalidArgumentException)) {
				return null;
			}
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"queryDeviceName failure");
			}
		}

		throw new TsapiPlatformException(4, 0, "queryDeviceName failure");
	}

	public TSProviderImpl getTSProviderImpl() {
		recreate();

		return this.provider;
	}

	public Vector<TSConnection> getTSConnections() {
		recreate();

		updateObject();
		return getConns();
	}

	Vector<TSConnection> getConns() {
		recreate();

		return (Vector<TSConnection>) this.connections.clone();
	}

	public Vector<TSConnection> getTSTerminalConnections() {
		recreate();

		updateObject();
		return getTermConns();
	}

	Vector<TSConnection> getTermConns() {
		recreate();

		return (Vector<TSConnection>) this.terminalConnections.clone();
	}

	public Vector<TSDevice> getTSTerminalDevices() {
		recreate();

		Vector<TSDevice> devVector = new Vector<TSDevice>();
		if (isTerminal()) {
			devVector.addElement(this);
		}
		return devVector;
	}

	public Vector<TSDevice> getTSAddressDevices() {
		recreate();

		Vector<TSDevice> devVector = new Vector<TSDevice>();

		devVector.addElement(this);
		return devVector;
	}

	public void addAddressMonitor(TsapiAddressMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (this.provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (this.obsSync) {
			if (this.addressMonitorThreads.contains(obs)) {
				return;
			}

			this.addressMonitorThreads.addElement(obs);
			try {
				setMonitor(false, false);

				obs.addReference();
			} catch (TsapiResourceUnavailableException e) {
				this.provider.removeAddressMonitorThread(obs);
				this.addressMonitorThreads.removeElement(obs);
				throw e;
			} catch (TsapiPlatformException e) {
				this.provider.removeAddressMonitorThread(obs);
				this.addressMonitorThreads.removeElement(obs);
				throw e;
			}

		}

		if (this.deviceType == 2) {
			Iterator<TSAgent> iterator = this.tsACDVector.iterator();
			while (iterator.hasNext()) {
				TSAgent agent = (TSAgent) iterator.next();
				agent.getParentAgent().addSkill(this);
			}
		}

		sendAddressSnapshot(obs);
	}

	public void removeAddressMonitor(TsapiAddressMonitor obs) {
		recreate();

		removeAddressMonitor(obs, 100, null);
	}

	protected void removeAddressMonitor(TsapiAddressMonitor obs, int cause,
			Object privateData) {
		recreate();

		if (this.addressMonitorThreads.removeElement(obs)) {
			obs.deleteReference(this, cause, privateData);
			testDelete();

			if (this.deviceType == 2) {
				Iterator<TSAgent> iterator = this.tsACDVector.iterator();
				while (iterator.hasNext()) {
					TSAgent agent = (TSAgent) iterator.next();
					agent.getParentAgent().deleteSkill(this);
				}
			}
		}
	}

	public void addTerminalMonitor(TsapiTerminalMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (this.provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (this.obsSync) {
			if (this.terminalMonitorThreads.contains(obs)) {
				return;
			}

			this.terminalMonitorThreads.addElement(obs);

			Iterator<TSAgent> iterator = this.tsAgentTermVector.iterator();
			while (iterator.hasNext()) {
				TSAgent agent = (TSAgent) iterator.next();
				agent.getParentAgent().populateSkillsVector();
			}

			try {
				setMonitor(false, false);

				obs.addReference();
			} catch (TsapiResourceUnavailableException e) {
				this.provider.removeTerminalMonitorThread(obs);
				this.terminalMonitorThreads.removeElement(obs);
				throw e;
			} catch (TsapiPlatformException e) {
				this.provider.removeTerminalMonitorThread(obs);
				this.terminalMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendTerminalSnapshot(obs);
	}

	public void removeTerminalMonitor(TsapiTerminalMonitor obs) {
		recreate();

		removeTerminalMonitor(obs, 100, null);
	}

	protected void removeTerminalMonitor(TsapiTerminalMonitor obs, int cause,
			Object privateData) {
		recreate();

		if (this.terminalMonitorThreads.removeElement(obs)) {
			obs.deleteReference(this, cause, privateData);
			testDelete();

			Iterator<TSAgent> iterator = this.tsAgentTermVector.iterator();
			while (iterator.hasNext()) {
				TSAgent agent = (TSAgent) iterator.next();
				agent.getParentAgent().clearSkillsVector();
			}
		}
	}

	public void addAddressCallMonitor(TsapiCallMonitor obs, boolean followCall)
			throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		addAddressCallMonitor(obs, followCall, false, null);
	}

	public void addAddressCallMonitor(TsapiCallMonitor obs, boolean followCall,
			boolean predictive, CSTAPrivate reqPriv)
			throws TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		recreate();

		if (followCall) {
			if (this.provider.getCapabilities().getMonitorCallsViaDevice() == 0) {
				throw new TsapiMethodNotSupportedException(0, 0,
						"unsupported by driver");
			}
			if ((predictive) && (!this.provider.getMonitorCallsViaDevice())) {
				throw new TsapiMethodNotSupportedException(0, 0,
						"unsupported by driver");
			}

		} else if (this.provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (this.obsSync) {
			if (this.callsViaAddressMonitorThreads.contains(obs)) {
				return;
			}

			if (followCall) {
				if (this.callsAtAddressMonitorThreads.removeElement(obs)) {
					this.callsViaAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(true, true, predictive, reqPriv);
					} catch (TsapiResourceUnavailableException e) {
						this.provider.removeCallMonitorThread(obs);
						this.callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (TsapiPlatformException e) {
						this.provider.removeCallMonitorThread(obs);
						this.callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					}

				} else {
					this.callsViaAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(true, true, predictive, reqPriv);

						obs.addReference();
					} catch (TsapiResourceUnavailableException e) {
						this.provider.removeCallMonitorThread(obs);
						this.callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (TsapiPlatformException e) {
						this.provider.removeCallMonitorThread(obs);
						this.callsViaAddressMonitorThreads.removeElement(obs);
						throw e;
					}
				}
			} else {
				synchronized (this.obsSync) {
					if (this.callsAtAddressMonitorThreads.contains(obs)) {
						return;
					}

					this.callsAtAddressMonitorThreads.addElement(obs);
					try {
						setMonitor(false, true);

						obs.addReference();
					} catch (TsapiResourceUnavailableException e) {
						this.provider.removeCallMonitorThread(obs);
						this.callsAtAddressMonitorThreads.removeElement(obs);
						throw e;
					} catch (TsapiPlatformException e) {
						this.provider.removeCallMonitorThread(obs);
						this.callsAtAddressMonitorThreads.removeElement(obs);
						throw e;
					}
				}
			}

		}

		sendCallSnapshot(obs, false);
	}

	Vector<TsapiCallMonitor> getCVDObservers() {
		recreate();

		Vector<TsapiCallMonitor> obs = (Vector<TsapiCallMonitor>) this.callsViaAddressMonitorThreads.clone();
		return obs;
	}

	public Vector<TsapiCallMonitor> getAddressCallObservers() {
		recreate();

		Vector<TsapiCallMonitor> obs = (Vector<TsapiCallMonitor>) this.callsViaAddressMonitorThreads.clone();
		for (int i = 0; i < this.callsAtAddressMonitorThreads.size(); i++) {
			obs.addElement(this.callsAtAddressMonitorThreads.elementAt(i));
		}
		return obs;
	}

	public void removeAddressCallMonitor(TsapiCallMonitor obs) {
		recreate();

		removeAddressCallMonitor(obs, 100, null);
	}

	protected void removeAddressCallMonitor(TsapiCallMonitor obs, int cause,
			Object privateData) {
		recreate();

		if (!this.callsViaAddressMonitorThreads.removeElement(obs)) {
			this.callsAtAddressMonitorThreads.removeElement(obs);
		}
		obs.deleteReference(this, false, cause, privateData);

		testDelete();
	}

	public void addTerminalCallMonitor(TsapiCallMonitor obs)
			throws TsapiResourceUnavailableException {
		recreate();

		if (this.provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(0, 0, 0,
					"unsupported by driver");
		}

		synchronized (this.obsSync) {
			if (this.callsAtTerminalMonitorThreads.contains(obs)) {
				return;
			}

			this.callsAtTerminalMonitorThreads.addElement(obs);
			try {
				setMonitor(false, true);

				obs.addReference();
			} catch (TsapiResourceUnavailableException e) {
				this.provider.removeCallMonitorThread(obs);
				this.callsAtTerminalMonitorThreads.removeElement(obs);
				throw e;
			} catch (TsapiPlatformException e) {
				this.provider.removeCallMonitorThread(obs);
				this.callsAtTerminalMonitorThreads.removeElement(obs);
				throw e;
			}
		}

		sendCallSnapshot(obs, true);
	}

	public Vector<TsapiCallMonitor> getTerminalCallObservers() {
		recreate();

		return (Vector<TsapiCallMonitor>) this.callsAtTerminalMonitorThreads.clone();
	}

	public void removeTerminalCallMonitor(TsapiCallMonitor obs) {
		recreate();

		removeTerminalCallMonitor(obs, 100, null);
	}

	protected void removeTerminalCallMonitor(TsapiCallMonitor obs, int cause,
			Object privateData) {
		recreate();

		this.callsAtTerminalMonitorThreads.removeElement(obs);

		obs.deleteReference(this, true, cause, privateData);

		testDelete();
	}

	public TsapiAddressCapabilities getTsapiAddressCapabilities() {
		recreate();

		return this.provider.getTsapiAddressCapabilities();
	}

	public TsapiTerminalCapabilities getTsapiTerminalCapabilities() {
		recreate();

		return this.provider.getTsapiTerminalCapabilities();
	}

	TerminalCapabilities getTerminalCapabilities(TSDevice cevice)
			throws TsapiInvalidArgumentException {
		recreate();

		return null;
	}

	public boolean getDoNotDisturb() throws TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getDoNotDisturbEvent() == 0) {
			if (this.provider.getCapabilities().getQueryDnd() == 0) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

			ConfHandler handler = new DNDConfHandler(this);
			try {
				this.provider.tsapi.queryDoNotDisturb(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return this.dndState;
			}
		}
		return this.dndState;
	}

	public void setDoNotDisturb(boolean enable, CSTAPrivate reqPriv)
			throws TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getSetDnd() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		ConfHandler handler = new DNDConfHandler(this, enable);
		try {
			this.provider.tsapi.setDnd(getName(), enable, reqPriv, handler);
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(), "setDnd failure");
			}
			throw new TsapiPlatformException(4, 0, "setDnd failure");
		}
	}

	public int getMessageWaitingBits() throws TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getMessageWaitingEvent() == 0) {
			if (this.provider.getCapabilities().getQueryMwi() == 0) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

			ConfHandler handler = new MWIConfHandler(this);
			try {
				this.provider.tsapi
						.queryMsgWaitingInd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return this.msgWaitingBits;
			}
		}

		return this.msgWaitingBits;
	}

	public void setMessageWaiting(boolean enable, CSTAPrivate reqPriv)
			throws TsapiInvalidStateException, TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getSetMwi() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		ConfHandler handler = new MWIConfHandler(this,
				this.msgWaitingBits |= 268435456);
		try {
			this.provider.tsapi.setMsgWaitingInd(getName(), enable, reqPriv,
					handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"setMsgWaitingInd failure");
			}
			throw new TsapiPlatformException(4, 0, "setMsgWaitingInd failure");
		}
	}

	public void setForwarding(Vector<TsapiCallControlForwarding> _fwdVector,
			CSTAPrivate reqPriv) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getSetFwd() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		cancelForwarding(null);

		CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];

		short forwardingType = 0;
		boolean forwardingOn = true;

		synchronized (_fwdVector) {
			for (int i = 0; i < _fwdVector.size(); i++) {
				TsapiCallControlForwarding fwd = (TsapiCallControlForwarding) _fwdVector
						.elementAt(i);
				String forwardDN = fwd.getDestinationAddress();
				switch (fwd.getType()) {
				case 2:
					switch (fwd.getFilter()) {
					case 3:
						forwardingType = 4;
						break;
					case 2:
						forwardingType = 3;
						break;
					case 1:
						forwardingType = 1;
					}

					break;
				case 3:
					switch (fwd.getFilter()) {
					case 3:
						forwardingType = 6;
						break;
					case 2:
						forwardingType = 5;
						break;
					case 1:
						forwardingType = 2;
					}

					break;
				case 1:
					forwardingType = 0;
				}

				fwdArray[0] = new CSTAForwardingInfo(forwardingType,
						forwardingOn, forwardDN);

				ConfHandler handler = new FwdConfHandler(this, fwdArray);
				try {
					this.provider.tsapi.setFwd(getName(), forwardingType,
							forwardingOn, forwardDN, reqPriv, handler);
				} catch (TsapiInvalidStateException e) {
					throw e;
				} catch (TsapiInvalidArgumentException e) {
					throw e;
				} catch (TsapiPlatformException e) {
					throw e;
				} catch (Exception e) {
					if ((e instanceof ITsapiException)) {
						throw new TsapiPlatformException(
								((ITsapiException) e).getErrorType(),
								((ITsapiException) e).getErrorCode(),
								"setFwd failure");
					}
					throw new TsapiPlatformException(4, 0, "setFwd failure");
				}
			}
		}
	}

	public Vector<TsapiCallControlForwarding> getForwarding()
			throws TsapiMethodNotSupportedException {
		recreate();

		if ((this.provider.getCapabilities().getForwardingEvent() == 0)
				|| (this.fwdVector.size() == 0)) {
			if (this.provider.getCapabilities().getQueryFwd() == 0) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

			ConfHandler handler = new FwdConfHandler(this);
			try {
				this.provider.tsapi.queryFwd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return this.fwdVector;
			}
		}

		return this.fwdVector;
	}

	public void cancelForwarding(CSTAPrivate reqPriv)
			throws TsapiInvalidStateException, TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getSetFwd() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		getForwarding();

		CSTAForwardingInfo[] fwdArray = new CSTAForwardingInfo[1];

		short forwardingType = 0;
		boolean forwardingOn = false;
		Vector<TsapiCallControlForwarding> fwdVectorClone;
		synchronized (this.fwdVector) {
			fwdVectorClone = (Vector<TsapiCallControlForwarding>) this.fwdVector.clone();
		}
		for (int i = 0; i < fwdVectorClone.size(); i++) {
			TsapiCallControlForwarding fwd = (TsapiCallControlForwarding) fwdVectorClone
					.elementAt(i);
			String forwardDN = fwd.getDestinationAddress();
			switch (fwd.getType()) {
			case 2:
				switch (fwd.getFilter()) {
				case 3:
					forwardingType = 4;
					break;
				case 2:
					forwardingType = 3;
					break;
				case 1:
					forwardingType = 1;
				}

				break;
			case 3:
				switch (fwd.getFilter()) {
				case 3:
					forwardingType = 6;
					break;
				case 2:
					forwardingType = 5;
					break;
				case 1:
					forwardingType = 2;
				}

				break;
			case 1:
				forwardingType = 0;
			}

			fwdArray[0] = new CSTAForwardingInfo(forwardingType, forwardingOn,
					forwardDN);
			Object handler = new FwdConfHandler(this, fwdArray);
			try {
				this.provider.tsapi
						.setFwd(getName(), forwardingType, forwardingOn,
								forwardDN, reqPriv, (ConfHandler) handler);
			} catch (TsapiInvalidStateException e) {
				throw e;
			} catch (TsapiPlatformException e) {
				throw e;
			} catch (Exception e) {
				if ((e instanceof ITsapiException)) {
					throw new TsapiPlatformException(
							((ITsapiException) e).getErrorType(),
							((ITsapiException) e).getErrorCode(),
							"setFwd failure");
				}
				throw new TsapiPlatformException(4, 0, "setFwd failure");
			}
		}
	}

	public TSConnection pickup(TSConnection pickConnection,
			TSDevice terminalAddress, CSTAPrivate privData)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getPickupCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		if (pickConnection.getTSCall().updateObject()) {
			int state = pickConnection.getTSConnState();
			if ((state != 50) && (state != 49) && (state != 54)) {
				throw new TsapiInvalidStateException(3, 0,
						TsapiCreateObject.getTsapiObject(pickConnection, true),
						2, state, "connection not alerting or in progress");
			}

		}

		ConfHandler handler = new PickupConfHandler(this, terminalAddress,
				pickConnection);
		try {
			this.provider.tsapi.pickupCall(pickConnection.getConnID(),
					terminalAddress.getName(), privData, handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiInvalidArgumentException e) {
			throw e;
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"pickupCall failure");
			}
			throw new TsapiPlatformException(4, 0, "pickupCall failure");
		}

		throw new TsapiPlatformException(4, 0,
				"Could not meet post-conditions of pickup()");
	}

	public TSConnection pickup(TSDevice pickAddress, TSDevice terminalAddress,
			CSTAPrivate privData) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getPickupCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		TSConnection pickConnection = null;

		pickAddress.updateObject();
		synchronized (pickAddress.connections) {
			for (int i = 0; i < pickAddress.connections.size(); i++) {
				pickConnection = (TSConnection) pickAddress.connections
						.elementAt(i);
				if ((pickConnection.getTSConnState() == 50)
						|| (pickConnection.getTSConnState() == 49)
						|| (pickConnection.getTSConnState() == 54)) {
					return pickup(pickConnection, terminalAddress, privData);
				}
			}
		}
		throw new TsapiInvalidArgumentException(3, 0,
				"no connection found to pickup");
	}

	public TSConnection groupPickup(TSDevice terminalAddress,
			CSTAPrivate privData) throws TsapiInvalidStateException,
			TsapiInvalidArgumentException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.getCapabilities().getGroupPickupCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		ConfHandler handler = new TermPrivConfHandler(this, 20);
		try {
			this.provider.tsapi.groupPickupCall(terminalAddress.getName(),
					privData, handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiInvalidArgumentException e) {
			throw e;
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"groupPickupCall failure");
			}
			throw new TsapiPlatformException(4, 0, "groupPickupCall failure");
		}

		throw new TsapiPlatformException(4, 0,
				"Could not meet post-conditions of groupPickup()");
	}

	void setTSAgent(String acdDeviceName, int state, String agentID,
			String password, CSTAPrivate reqPriv)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException {
		recreate();
		int agentMode;
		switch (state) {
		case 1:
			agentMode = 0;
			break;
		case 2:
			agentMode = 1;
			break;
		case 4:
			agentMode = 3;
			break;
		case 3:
			agentMode = 2;
			break;
		case 6:
			agentMode = 5;
			break;
		case 5:
			agentMode = 4;
			break;
		case 0:
		case 7:
		default:
			throw new TsapiPlatformException(4, 0, "Unknown Agent state ["
					+ state + "]");
		}

		ConfHandler handler = new LucentSetAgentStateConfHandler(this);
		try {
			this.provider.tsapi.setAgentState(getName(), (short) agentMode,
					agentID, acdDeviceName, password, reqPriv, handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiInvalidArgumentException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"setAgentState failure");
			}
			throw new TsapiPlatformException(4, 0, "setAgentState failure");
		}
	}

	void addToAgentTermVector(TSAgent tsAgent) {
		recreate();

		this.tsAgentTermVector.addElement(tsAgent);
	}

	void addToACDVector(TSAgent tsAgent) {
		recreate();

		this.tsACDVector.addElement(tsAgent);
	}

	void removeFromAgentTermVector(TSAgent tsAgent) {
		recreate();

		this.tsAgentTermVector.removeElement(tsAgent);
		testDelete();
	}

	void removeFromACDVector(TSAgent tsAgent) {
		recreate();

		this.tsACDVector.removeElement(tsAgent);
		testDelete();
	}

	private LucentSetAgentState createLucentSetAgentState(short workMode,
			int reasonCode) {
		if (this.provider.isLucentV5()) {
			return new LucentV5SetAgentState(workMode, reasonCode);
		}
		return new LucentSetAgentState(workMode);
	}

	private LucentSetAgentState createLucentSetAgentState(short workMode) {
		return createLucentSetAgentState(workMode, 0);
	}

	public TSAgent addTSAgent(TSDevice tsACDDevice, int initialState,
			int workMode, int reasonCode, String agentID, String password,
			CSTAPrivate reqPriv) throws TsapiInvalidArgumentException,
			TsapiInvalidStateException {
		recreate();

		String acdName = null;

		if (tsACDDevice != null) {
			acdName = tsACDDevice.getName();
		}

		TSAgentKey agentKey = null;
		agentKey = new TSAgentKey(getName(), acdName, agentID);
		TSAgent tsAgent = null;
		tsAgent = this.provider.findAgent(agentKey);
		if (tsAgent != null) {
			return tsAgent;
		}

		if ((initialState != 4) && (initialState != 3) && (initialState != 1)) {
			throw new TsapiInvalidArgumentException(3, 0,
					"initial state not valid");
		}

		int state = 1;

		boolean reqPrivPresent = false;
		if (this.provider.isLucent()) {
			if (reqPriv != null) {
				reqPrivPresent = true;
			} else {
				LucentSetAgentState lsas = null;
				if (workMode == 1) {
					lsas = createLucentSetAgentState((short) 3);
				} else if (workMode == 2) {
					lsas = createLucentSetAgentState((short) 4);
				} else if (initialState == 4) {
					workMode = 1;
					lsas = createLucentSetAgentState((short) 3);
				} else if ((initialState == 3) || (initialState == 1)) {
					workMode = 0;

					lsas = createLucentSetAgentState((short) 1, reasonCode);
				}

				reqPriv = lsas.makeTsapiPrivate();
			}

		}

		try {
			setTSAgent(acdName, state, agentID, password, reqPriv);

			tsAgent = this.provider.createAgent(agentKey, agentID, password);
			if (tsAgent.getInternalState() == 2) {
				this.provider.dumpAgent(agentKey);
				tsAgent = this.provider
						.createAgent(agentKey, agentID, password);
			}

			if (this.provider.isLucent()) {
				if (!reqPrivPresent) {
					tsAgent.updateState(initialState, workMode, reasonCode,
							null);
				}
			} else {
				tsAgent.updateState(1, 0, 0, null);
			}
			tsAgent.getState();
			return tsAgent;
		} catch (TsapiInvalidStateException e) {
			if ((acdName != null) && (e.getErrorType() == 2)
					&& (e.getErrorCode() == 21)) {
				snapshotAgentsInTerminal(acdName, agentID);

				agentKey = new TSAgentKey(getName(), acdName, null);
				tsAgent = this.provider.findAgent(agentKey);
				if (tsAgent != null) {
					return tsAgent;
				}

				throw new TsapiPlatformException(4, 0, "add Agent failure");
			}
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 22)) {
				throw new TsapiPlatformException(4, 0,
						"Agent is already logged into another Station");
			}

			throw new TsapiPlatformException(4, 0, "add Agent failure");
		} catch (TsapiPlatformException e) {
			if ((acdName != null) && (e.getErrorType() == 2)
					&& (e.getErrorCode() == 1)) {
				snapshotAgentsInTerminal(acdName, agentID);

				agentKey = new TSAgentKey(getName(), acdName, null);
				tsAgent = this.provider.findAgent(agentKey);
				if (tsAgent != null) {
					return tsAgent;
				}

				throw new TsapiPlatformException(4, 0,
						"There is already an Agent logged on at the station");
			}
			if ((acdName == null) && (e.getErrorType() == 2)
					&& (e.getErrorCode() == 1)) {
				throw new TsapiPlatformException(4, 0,
						"There is already an Agent logged on at the station");
			}
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 0)) {
				throw new TsapiPlatformException(4, 0,
						"An attempt to log in an ACD agent with an incorrect password");
			}
			if ((e.getErrorType() == 2) && (e.getErrorCode() == 12)) {
				throw new TsapiPlatformException(4, 0,
						"Invalid AgentId is specified");
			}

			throw e;
		}
	}

	public void removeTSAgent(TSAgent tsAgent, int reasonCode)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException {
		recreate();

		if (tsAgent == null) {
			if (this.tsAgentTermVector.size() == 0) {
				throw new TsapiInvalidArgumentException(3, 0,
						"No agents logged into specified Terminal");
			}

			Vector<TSAgent> agentVector = new Vector<TSAgent>(this.tsAgentTermVector);
			for (int i = 0; i < agentVector.size(); i++) {
				tsAgent = (TSAgent) agentVector.elementAt(i);
				if (tsAgent.getState() != 2) {
					tsAgent.setState(2, 0, reasonCode);
				}
			}

		} else {
			if (tsAgent.getState() == 2) {
				return;
			}

			if (!this.tsAgentTermVector.contains(tsAgent)) {
				throw new TsapiInvalidArgumentException(3, 0,
						"Agent not logged into specified Terminal");
			}

			tsAgent.setState(2, 0, reasonCode);
		}
	}

	void snapshotAgentsInACD() {
		recreate();

		if (!this.provider.isLucent())
			return;
		try {
			LucentQueryAgentLogin lqal = new LucentQueryAgentLogin(getName());
			synchronized (this) {
				this.waitingForAgents = true;
			}
			ConfHandler handler = new QueryAgentLoginConfHandler(this);
			Object lqalConf = this.provider.sendPrivateData(
					lqal.makeTsapiPrivate(), handler);

			if ((lqalConf instanceof LucentQueryAgentLoginConfEvent)) {
				synchronized (this) {
					if (this.waitingForAgents) {
						try {
							wait(TSProviderImpl.DEFAULT_TIMEOUT);
						} catch (InterruptedException e) {
						}
						if (this.waitingForAgents) {
							this.waitingForAgents = false;
							throw new TsapiPlatformException(4, 0,
									"snapshot time-out");
						}
					}
				}
				for (int i = 0; i < this.tsACDVector.size(); i++) {
					((TSAgent) this.tsACDVector.elementAt(i)).getState();
				}
			}
		} catch (Exception e) {
		}
	}

	void handleAgentLoginResponse(LucentQueryAgentLoginResp event) {
		recreate();

		int xrefID = event.getPrivEventCrossRefID();
		if ((event.getDevices() == null) || (event.getDevices().length == 0)) {
			this.provider.deletePrivateXref(xrefID);
			synchronized (this) {
				if (this.waitingForAgents) {
					this.waitingForAgents = false;
					notify();
				}
			}
		} else {
			for (int i = 0; i < event.getDevices().length; i++) {
				TSDevice agentDevice = this.provider.createDevice(event
						.getDevices()[i]);
				String agentID = agentDevice.getAgentID();
				TSAgentKey agentKey = new TSAgentKey(event.getDevices()[i],
						getName(), agentID);
				TSAgent agent = this.provider.createAgent(agentKey, "", "");
				if (agent.getInternalState() == 2) {
					this.provider.dumpAgent(agentKey);
					agent = this.provider.createAgent(agentKey, "", "");
				}
			}
		}
	}

	void snapshotAgentsInTerminal(String acdName, String agentID) {
		recreate();

		if (this.provider.getCapabilities().getQueryAgentState() != 0) {
			QueryAgentStateConfHandler2 handler = new QueryAgentStateConfHandler2(
					this, acdName, agentID);
			CSTAPrivate priv = null;
			if ((this.provider.isLucent()) && (acdName != null)) {
				LucentQueryAgentState lqas = new LucentQueryAgentState(acdName);
				priv = lqas.makeTsapiPrivate();
			} else if (this.provider.isLucent()) {
				TSAgentKey agentKey = new TSAgentKey(getName(), acdName,
						agentID);
				TSAgent agent = null;
				agent = getTSProviderImpl().findAgent(agentKey);
				if (agent == null) {
					synchronized (this.tsAgentTermVector) {
						if (this.tsAgentTermVector.size() > 0) {
							agent = (TSAgent) this.tsAgentTermVector
									.elementAt(0);
						}
					}
				}

				if ((agent != null) && (agent.getACDDeviceID() != null)) {
					LucentQueryAgentState lqas = new LucentQueryAgentState(
							agent.getACDDeviceID().getName());
					priv = lqas.makeTsapiPrivate();
				}
			}
			try {
				this.provider.tsapi.queryAgentState(getName(), priv, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			if (acdName == null) {
				agentID = getAgentID();
				TSAgentKey currentAgentKey = new TSAgentKey(getName(), acdName,
						agentID);
				if (agentID != null) {
					if (this.tsAgentTermVector.size() > 0) {
						TSAgent previousAgent = (TSAgent) this.tsAgentTermVector
								.get(0);
						TSAgentKey previousTsAgentKey = previousAgent
								.getAgentKey();
						if (!previousTsAgentKey.equals(currentAgentKey)) {
							log.info("deleting agent "
									+ previousAgent.getAgentID());
							previousAgent.updateState(2, -1, 0, null);
						}
					}
				}
			}
			TSAgentKey agentKey = new TSAgentKey(getName(), acdName, agentID);
			TSAgent agent = null;

			if (handler.getAgentState() == -1) {
				return;
			}

			if (handler.getAgentState() == 1) {
				agent = getTSProviderImpl().findAgent(agentKey);
				if (agent == null) {
					synchronized (this.tsAgentTermVector) {
						if (this.tsAgentTermVector.size() > 0) {
							agent = (TSAgent) this.tsAgentTermVector
									.elementAt(0);
						}
					}
				}
				if (agent != null) {
					agent.updateState(2, handler.getWorkMode(), 0, null);
				}
				return;
			}

			agent = getTSProviderImpl().createAgent(agentKey, agentID, null);

			TSDevice agentDevice = agent.getTSAgentDevice();
			TSDeviceState tsdeviceState = agentDevice.curState;
			if (tsdeviceState.wasDeleteDone()) {
				updateAllAgentsInDeletedDeviceInstance(agentDevice.tsAgentTermVector);
			}
			if (agent.getInternalState() == 2) {
				this.provider.dumpAgent(agentKey);
				agent = this.provider.createAgent(agentKey, agentID, null);
			}

			int state = 0;
			if (handler.getState() == 7) {
				state = 7;
			} else {
				switch (handler.getAgentState()) {
				case 0:
					state = 3;
					break;
				case 1:
					state = 2;
					break;
				case 2:
					state = 4;
					break;
				case 3:
					state = 5;
					break;
				case 4:
					state = 6;
					break;
				}

			}

			agent.updateState(state, handler.getWorkMode(), 0, null);
		}
	}

	private void updateAllAgentsInDeletedDeviceInstance(
			Vector<TSAgent> agentsForAgentTerm) {
		for (int i = 0; i < agentsForAgentTerm.size(); i++) {
			TSAgent agent = (TSAgent) agentsForAgentTerm.elementAt(i);
			log.info("agent represented by agentKey "
					+ agent.getAgentKey()
					+ " is holding reference to agentDevice which is in deleted state");
			agent.agentDevice = this;
			log.info("updated the agent device reference to the new agentdevice "
					+ getName());
			addToAgentTermVector(agent);
			log.debug("agent has been added in the list of agents for device "
					+ getName());
		}
	}

	public Vector<TSAgent> getTSAgentsForAgentTerm() {
		recreate();

		if ((this.monitorDeviceXRefID == 0) || (this.provider.isLucent()))
			snapshotAgentsInTerminal(null, null);
		return this.tsAgentTermVector;
	}

	public Vector<TSAgent> getTSAgentsForACDAddr() {
		recreate();

		if (this.monitorDeviceXRefID == 0) {
			snapshotAgentsInACD();
		}

		return this.tsACDVector;
	}

	public int getTSNumberQueued() throws TsapiMethodNotSupportedException {
		recreate();

		if (this.provider.isLucent()) {
			try {
				LucentQueryAcdSplit lqas = new LucentQueryAcdSplit(getName());
				Object lqasConf = this.provider.sendPrivateData(lqas
						.makeTsapiPrivate());
				if ((lqasConf instanceof LucentQueryAcdSplitConfEvent)) {
					return ((LucentQueryAcdSplitConfEvent) lqasConf)
							.getCallsInQueue();
				}
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			} catch (Exception e) {
				throw new TsapiMethodNotSupportedException(4, 0,
						"unsupported by driver");
			}

		}

		return this.numQueued;
	}

	public int getNumberQueued() {
		recreate();

		return this.numQueued;
	}

	void setNumberQueued(int _numQueued) {
		recreate();

		this.numQueued = _numQueued;
	}

	public TSCall getTSOldestCallQueued()
			throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public int getTSRelativeQueueLoad() throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public int getTSQueueWaitTime() throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public TSDevice[] getTSACDManagerDevice()
			throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public Vector<TSDevice> getTSACDDevices()
			throws TsapiMethodNotSupportedException {
		recreate();

		throw new TsapiMethodNotSupportedException(4, 0,
				"unsupported by implementation");
	}

	public void addRouteMonitor(TsapiRouteMonitor observer) {
		recreate();

		synchronized (this.obsSync) {
			registerRoute();

			this.tsRouteCallback = observer;
			this.tsRouteCallback.addReference();
		}
	}

	void registerRoute() {
		recreate();

		ConfHandler handler = new RouteRegisterConfHandler(this);
		try {
			if (getName().equals("AllRouteAddress"))
				this.provider.tsapi.registerRouteCallback(null, null, handler);
			else
				this.provider.tsapi.registerRouteCallback(getName(), null,
						handler);
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"registerRouteCallback failure");
			}
			throw new TsapiPlatformException(4, 0,
					"registerRouteCallback failure");
		}
		this.provider.addRoute(this.registerReqID, this);
		this.sessionHash = new Hashtable<Integer, TSRouteSession>(3);
	}

	public void removeRouteMonitor(TsapiRouteMonitor observer) {
		recreate();

		removeRouteMonitor(observer, 100, null);
	}

	protected void removeRouteMonitor(TsapiRouteMonitor observer, int cause,
			Object privateData) {
		recreate();
		try {
			this.provider.tsapi.cancelRouteCallback(this.registerReqID, null,
					null);
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if ((e instanceof ITsapiException)) {
				throw new TsapiPlatformException(
						((ITsapiException) e).getErrorType(),
						((ITsapiException) e).getErrorCode(),
						"cancelRouteCallback failure");
			}
			throw new TsapiPlatformException(4, 0,
					"cancelRouteCallback failure");
		}
		observer.deleteReference(this);
		testDelete();
	}

	public Vector<TSRouteSession> getTSRouteSessions() {
		recreate();

		if (this.tsRouteCallback != null) {
			return getSessions();
		}
		return null;
	}

	int getRegisterReqID() {
		recreate();

		return this.registerReqID;
	}

	void addSession(int routingCrossRefID, TSRouteSession routeSession) {
		recreate();

		if (this.sessionHash != null) {
			Object oldObj = this.sessionHash.put(
					new Integer(routingCrossRefID), routeSession);
			if (oldObj != null)
				log.info("NOTICE: sessionHash.put() replaced " + oldObj
						+ " for " + this);
		}
	}

	void deleteSession(int routingCrossRefID) {
		recreate();

		if (this.sessionHash != null) {
			this.sessionHash.remove(new Integer(routingCrossRefID));
		}
	}

	Vector<TSRouteSession> getSessions() {
		recreate();

		if (this.sessionHash == null) {
			return null;
		}

		Vector<TSRouteSession> sessionVector = new Vector<TSRouteSession>();
		Enumeration<TSRouteSession> sessionEnum = this.sessionHash.elements();
		while (sessionEnum.hasMoreElements()) {
			try {
				sessionVector.addElement((TSRouteSession) sessionEnum
						.nextElement());
			} catch (NoSuchElementException e) {
				log.error(e.getMessage(), e);
			}
		}

		return sessionVector;
	}

	TsapiRouteMonitor getTSRouteCallback() {
		recreate();
		synchronized (this.obsSync) {
			return this.tsRouteCallback;
		}
	}

	public Object getAddrPrivateData() {
		recreate();

		if ((this.replyAddrPriv instanceof CSTAPrivate))
			return this.replyAddrPriv;
		return null;
	}

	Object getTermPrivateData() {
		recreate();

		if ((this.replyTermPriv instanceof CSTAPrivate))
			return this.replyTermPriv;
		return null;
	}

	public Object sendPrivateData(CSTAPrivate data) {
		recreate();
		try {
			return this.provider.sendPrivateData(data);
		} catch (Exception e) {
		}
		throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
	}

	private void setState(TSDeviceState _newState) {
		log.info("TSDevice state transition: " + this.curState + " -> "
				+ _newState + ", device " + this);

		this.curState = _newState;
	}

	TSDevice(TSProviderImpl _provider, CSTAExtendedDeviceID deviceID) {
		this.asyncInitializationComplete = false;

		this.provider = _provider;
		this.dndInitialized = false;
		this.mwiInitialized = false;
		this.forwardingInitialized = false;
		this.dndState = false;
		this.msgWaitingBits = 0;
		this.numQueued = 0;
		this.isATerminal = true;
		this.isExternal = false;
		this.deviceType = 0;
		g_CreationCnt += 1;

		this.devNameVector = new Vector<CSTAExtendedDeviceID>();
		this.devNameVector.addElement(deviceID);
		this.connections = new Vector<TSConnection>();
		this.terminalConnections = new Vector<TSConnection>();
		this.internalDeviceMonitors = new Vector<TSCall>();
		this.terminalMonitorThreads = new Vector<TsapiTerminalMonitor>();
		this.addressMonitorThreads = new Vector<TsapiAddressMonitor>();
		this.callsViaAddressMonitorThreads = new Vector<TsapiCallMonitor>();
		this.callsAtAddressMonitorThreads = new Vector<TsapiCallMonitor>();
		this.callsAtTerminalMonitorThreads = new Vector<TsapiCallMonitor>();
		this.tsACDVector = new Vector<TSAgent>();
		this.tsAgentTermVector = new Vector<TSAgent>();
		this.fwdVector = new Vector<TsapiCallControlForwarding>();
		this.callsWaitingForConnectPostConditions = new Vector<TSCall>();

		this.curState = new TSDeviceStateActive();

		this.obsSync = new Object();

		log.info("Constructing device " + this + " with name " + getName()
				+ " for " + this.provider);
		try {
			if (((this.provider.isLucent()) && (getName().regionMatches(true,
					0, "T", 0, 1)))
					|| (((!this.provider.isLucent()) || (deviceID
							.getDeviceIDType() != 55))
							&& ((!this.provider.isLucent()) || (deviceID
									.getDeviceIDType() != 40)) && ((!this.provider
							.isLucent()) || (deviceID.getDeviceIDType() != 0)))) {
				setIsATerminal(false);
				setIsExternal(true);
				notifyAsyncInitializationComplete();
			} else if (this.provider.getCapabilities().getQueryDeviceInfo() != 0) {
				this.provider.tsapi.queryDeviceInfoAsync(getName(), null,
						new AsyncQueryDeviceInfoConfHandler(this));
			} else {
				notifyAsyncInitializationComplete();
			}
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			setIsATerminal(false);
			setIsExternal(true);
			notifyAsyncInitializationComplete();
		}
	}

	synchronized void waitForAsyncInitialization() {
		recreate();

		if (!this.asyncInitializationComplete) {
			try {
				log.info(this + " waiting for initialization to complete");
				wait(TSProviderImpl.DEFAULT_TIMEOUT);
			} catch (InterruptedException e) {
			}
			if (!this.asyncInitializationComplete) {
				throw new TsapiPlatformException(4, 0,
						"could not finish address/terminal construction");
			}
		}
	}

	boolean updateObject() {
		recreate();

		if (isMonitorSet() == true) {
			if (cleanUCIDsInCallsInConnections()) {
				return true;
			}
		}

		return doCallSnapshot();
	}

	boolean cleanUCIDsInCallsInConnections() {
		boolean bfound = false;
		Vector<TSConnection> conns = new Vector<TSConnection>(this.connections);
		for (int i = 0; i < conns.size(); i++) {
			TSConnection conn = (TSConnection) conns.elementAt(i);
			TSCall call = conn.getTSCall();

			if (call.state != 34) {
				if (!call.cleanUCIDInCall()) {
					bfound = true;
				}
			}
		}

		return !bfound;
	}

	void setMonitor(boolean followCall, boolean callObserver)
			throws TsapiResourceUnavailableException {
		setMonitor(followCall, callObserver, false, null);
	}

	void setMonitor(boolean followCall, boolean callObserver,
			boolean predictive, CSTAPrivate reqPriv)
			throws TsapiResourceUnavailableException {
		recreate();
		CSTAEvent event;
		if ((followCall)
				|| ((callObserver) && ((getDeviceType() == 2) || (getDeviceType() == 1)))) {
			synchronized (this) {
				if (this.monitorCallsViaDeviceXRefID != 0) {
					return;
				}
				if (this.provider.getCapabilities().getMonitorCallsViaDevice() != 0) {
					try {
						event = this.provider.tsapi.monitorCallsViaDevice(
								getName(), new CSTAMonitorFilter(), reqPriv);
						if (event != null) {
							CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
									.getEvent();
							this.monitorCallsViaDeviceXRefID = monitorConf
									.getMonitorCrossRefID();
							this.monitorPredictiveCallsViaDevice = predictive;
						}
					} catch (TsapiUnableToSendException tue) {
						throw tue;
					} catch (Exception e) {
						log.error("MonitorCallsViaDevice request failed - retrying");
						try {
							event = this.provider.tsapi
									.monitorCallsViaDevice(getName(),
											new CSTAMonitorFilter(), reqPriv);
							CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
									.getEvent();
							this.monitorCallsViaDeviceXRefID = monitorConf
									.getMonitorCrossRefID();
							this.monitorPredictiveCallsViaDevice = predictive;
						} catch (TsapiResourceUnavailableException e1) {
							throw e1;
						} catch (Exception e1) {
							if (followCall) {
								throw new TsapiResourceUnavailableException(2,
										0, 0,
										"failure to monitor calls via device");
							}
						}

					}

				}

			}

		}

		if ((!followCall)
				&& ((!this.provider.isLucent()) || ((this.provider.isLucent()) && (getDeviceType() != 1)))) {
			if (this.provider.getCapabilities().getMonitorDevice() == 0) {
				throw new TsapiResourceUnavailableException(2, 0, 0,
						"no capability to monitor device");
			}
			synchronized (this) {
				if (this.monitorDeviceXRefID != 0) {
					return;
				}

				try {
					event = this.provider.tsapi.monitorDevice(getName(),
							new CSTAMonitorFilter(), null);
				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (Exception e) {
					log.error("MonitorDevice request failed - retrying", e);
					log.info("MonitorDevice request failed - retrying");
					try {
						event = this.provider.tsapi.monitorDevice(getName(),
								new CSTAMonitorFilter(), null);
					} catch (TsapiUnableToSendException tue) {
						throw tue;
					} catch (TsapiResourceUnavailableException e1) {
						throw e1;
					} catch (Exception e1) {
						throw new TsapiResourceUnavailableException(2, 0, 0,
								"failure to monitor device");
					}
				}
				if (event != null) {
					CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
							.getEvent();
					this.monitorDeviceXRefID = monitorConf
							.getMonitorCrossRefID();
				}
			}

		}

		if ((followCall)
				|| ((callObserver) && ((getDeviceType() == 2) || (getDeviceType() == 1)))) {
			this.provider.addMonitor(this.monitorCallsViaDeviceXRefID, this);
		}

		if ((!followCall)
				&& ((!this.provider.isLucent()) || ((this.provider.isLucent()) && (getDeviceType() != 1)))) {
			doTerminalSnapshot();
			doAddressSnapshot();
			doCallSnapshot();
			this.provider.addMonitor(this.monitorDeviceXRefID, this);
		}
	}

	void removeInternalMonitor(TSCall call) {
		recreate();

		this.internalDeviceMonitors.removeElement(call);

		testDelete();
	}

	TSDevice setInternalMonitor(TSCall call)
			throws TsapiResourceUnavailableException {
		recreate();

		if (this.provider.getCapabilities().getMonitorDevice() == 0) {
			throw new TsapiResourceUnavailableException(2, 0, 0,
					"no capability to monitor device");
		}
		synchronized (this.obsSync) {
			if (isMonitorSet()) {
				this.internalDeviceMonitors.addElement(call);
				return this;
			}
			synchronized (this) {
				if (this.monitorDeviceXRefID != 0) {
					this.internalDeviceMonitors.addElement(call);
					return this;
				}

				CSTAEvent event;
				try {
					event = this.provider.tsapi.monitorDevice(getName(),
							new CSTAMonitorFilter(), null);
				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (TsapiResourceUnavailableException e) {
					throw e;
				} catch (Exception e) {
					throw new TsapiResourceUnavailableException(2, 0, 0,
							"failure to monitor device");
				}

				CSTAMonitorConfEvent monitorConf = (CSTAMonitorConfEvent) event
						.getEvent();
				this.monitorDeviceXRefID = monitorConf.getMonitorCrossRefID();
			}

			doTerminalSnapshot();
			doAddressSnapshot();
			doCallSnapshot();

			this.provider.addMonitor(this.monitorDeviceXRefID, this);

			this.internalDeviceMonitors.addElement(call);
		}
		return this;
	}

	boolean doTerminalSnapshot() {
		recreate();

		if (!isTerminal()) {
			return true;
		}

		snapshotAgentsInTerminal(null, null);

		return true;
	}

	boolean doAddressSnapshot() {
		recreate();

		if (getDeviceType() == 1) {
			return true;
		}

		if ((this.provider.getCapabilities().getQueryDnd() != 0)
				&& (getDeviceType() != 2)) {
			ConfHandler handler = new DNDConfHandler(this);
			try {
				this.provider.tsapi.queryDoNotDisturb(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		if (this.provider.getCapabilities().getQueryMwi() != 0) {
			ConfHandler handler = new MWIConfHandler(this);
			try {
				this.provider.tsapi
						.queryMsgWaitingInd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		if (this.provider.getCapabilities().getQueryFwd() != 0) {
			ConfHandler handler = new FwdConfHandler(this);
			try {
				this.provider.tsapi.queryFwd(getName(), null, handler);
			} catch (TsapiUnableToSendException tue) {
				throw tue;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		snapshotAgentsInACD();

		return true;
	}

	boolean doCallSnapshot() {
		recreate();

		if ((this.provider.getCapabilities().getSnapshotDeviceReq() == 0)
				|| (getDeviceType() != 0)) {
			return false;
		}

		SnapshotDeviceConfHandler handler = new SnapshotDeviceConfHandler(this);
		try {
			this.provider.tsapi.snapshotDevice(getName(), null, handler);
		} catch (TsapiUnableToSendException tue) {
			throw tue;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}

		Vector<TSCall> newCalls = new Vector<TSCall>();

		if (handler.info != null) {
			TSCall call = null;
			for (int i = 0; i < handler.info.length; i++) {
				try {
					call = this.provider.createCall(handler.info[i]
							.getCallIdentifier().getCallID());
					if (call.getTSState() == 34) {
						this.provider.dumpCall(handler.info[i]
								.getCallIdentifier().getCallID());
						call = this.provider.createCall(handler.info[i]
								.getCallIdentifier().getCallID());
					}
					call.doSnapshot(handler.info[i].getCallIdentifier(), null,
							true);
					if (isMonitorSet()) {
						call.setNeedSnapshot(false);
					}

				} catch (TsapiPlatformException e) {
					continue;
				}

				if (!newCalls.contains(call)) {
					newCalls.addElement(call);
				}

			}

		}

		Vector<TSConnection> conns = new Vector<TSConnection>(this.connections);
		for (int i = 0; i < conns.size(); i++) {
			TSConnection conn = (TSConnection) conns.elementAt(i);
			if (!newCalls.contains(conn.getTSCall())) {
				conn.setConnectionState(89, null);

				if (this.connections.removeElement(conn))
					log.debug("removed connection which was already in disconnected state");
			}
		}
		conns = new Vector<TSConnection>(this.terminalConnections);

		for (int i = 0; i < conns.size(); i++) {
			TSConnection conn = (TSConnection) conns.elementAt(i);
			if (!newCalls.contains(conn.getTSCall())) {
				conn.setTermConnState(102, null);

				if (this.terminalConnections.removeElement(conn)) {
					log.debug("removed a terminalConnection which was already in dropped state");
				}
			}
		}
		return true;
	}

	void sendCallSnapshot(TsapiCallMonitor callback, boolean forTerminal) {
		recreate();

		if (callback == null) {
			return;
		}

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		if (forTerminal) {
			synchronized (this.terminalConnections) {
				for (int i = 0; i < this.terminalConnections.size(); i++) {
					TSCall call = ((TSConnection) this.terminalConnections
							.elementAt(i)).getTSCall();

					if (!call.getCallObservers().contains(callback)) {
						call.getSnapshot(eventList);
						call.addDeviceObservers(this,
								this.callsAtTerminalMonitorThreads, null, null,
								false);
					}
				}
			}
		} else {
			synchronized (this.connections) {
				for (int i = 0; i < this.connections.size(); i++) {
					TSCall call = ((TSConnection) this.connections.elementAt(i))
							.getTSCall();

					if (!call.getCallObservers().contains(callback)) {
						call.getSnapshot(eventList);
						call.addDeviceObservers(this, null,
								this.callsAtAddressMonitorThreads,
								this.callsViaAddressMonitorThreads, false);
					}
				}
			}
		}
		if (eventList.size() > 0) {
			callback.deliverEvents(eventList, 110, true);
		}
	}

	void sendAddressSnapshot(TsapiAddressMonitor callback) {
		recreate();

		if (callback == null) {
			return;
		}

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		eventList.addElement(new TSEvent(37, this));
		eventList.addElement(new TSEvent(38, this));
		eventList.addElement(new TSEvent(39, this));

		for (int i = 0; i < this.tsACDVector.size(); i++) {
			((TSAgent) this.tsACDVector.elementAt(i)).getSnapshot(eventList,
					false);
		}

		if (eventList.size() > 0) {
			for (TSEvent tsEvent : eventList) {
				if ((tsEvent.getEventTarget() instanceof TSAgent)) {
					tsEvent.setSkillDevice(this);
				}
			}

			callback.deliverEvents(eventList, true);
		}
	}

	void sendTerminalSnapshot(TsapiTerminalMonitor callback) {
		recreate();

		if (callback == null) {
			return;
		}

		Vector<TSEvent> eventList = new Vector<TSEvent>();

		eventList.addElement(new TSEvent(59, this));

		for (int i = 0; i < this.tsAgentTermVector.size(); i++) {
			((TSAgent) this.tsAgentTermVector.elementAt(i)).getSnapshot(
					eventList, true);
		}

		if (eventList.size() > 0) {
			callback.deliverEvents(eventList, true);
		}
	}

	void updateDNDState(boolean _dndState, Vector<TSEvent> eventList) {
		recreate();

		synchronized (this) {
			if (this.dndState == _dndState) {
				if (!this.dndInitialized)
					this.dndInitialized = true;
				return;
			}

			this.dndState = _dndState;
		}

		if ((eventList != null) && (this.dndInitialized)) {
			eventList.addElement(new TSEvent(37, this));
			eventList.addElement(new TSEvent(59, this));
		}

		if (!this.dndInitialized)
			this.dndInitialized = true;
	}

	void updateMessageWaitingBits(int _msgWaitingBits, Vector<TSEvent> eventList) {
		recreate();

		synchronized (this) {
			if (this.msgWaitingBits == _msgWaitingBits) {
				if (!this.mwiInitialized)
					this.mwiInitialized = true;
				return;
			}

			this.msgWaitingBits = _msgWaitingBits;
		}

		if ((eventList != null) && (this.mwiInitialized)) {
			eventList.addElement(new TSEvent(38, this));
		}

		if (!this.mwiInitialized)
			this.mwiInitialized = true;
	}

	void updateForwarding(CSTAForwardingInfo[] fwdInfo,
			Vector<TSEvent> eventList) {
		recreate();

		boolean update = false;
		boolean typeMatch = false;
		synchronized (this) {
			TsapiCallControlForwarding newFwd = null;
			TsapiCallControlForwarding oldFwd = null;
			for (int i = 0; i < fwdInfo.length; i++) {
				switch (fwdInfo[i].getForwardingType()) {
				case 1:
					newFwd = new TsapiCallControlForwarding(
							fwdInfo[i].getForwardDN(), 2);

					break;
				case 4:
					newFwd = new TsapiCallControlForwarding(
							fwdInfo[i].getForwardDN(), 2, false);

					break;
				case 3:
					newFwd = new TsapiCallControlForwarding(
							fwdInfo[i].getForwardDN(), 2, true);

					break;
				case 0:
					newFwd = new TsapiCallControlForwarding(
							fwdInfo[i].getForwardDN(), 1);

					break;
				case 2:
					newFwd = new TsapiCallControlForwarding(
							fwdInfo[i].getForwardDN(), 3);

					break;
				case 6:
					newFwd = new TsapiCallControlForwarding(
							fwdInfo[i].getForwardDN(), 3, false);

					break;
				case 5:
					newFwd = new TsapiCallControlForwarding(
							fwdInfo[i].getForwardDN(), 3, true);
				}

				typeMatch = false;
				synchronized (this.fwdVector) {
					for (int j = 0; j < this.fwdVector.size(); j++) {
						oldFwd = (TsapiCallControlForwarding) this.fwdVector
								.elementAt(j);
						if ((oldFwd.getType() == newFwd.getType())
								&& (oldFwd.getFilter() == newFwd.getFilter())) {
							typeMatch = true;
							if (!fwdInfo[i].isForwardingOn()) {
								update = true;

								this.fwdVector.removeElement(oldFwd);
								break;
							}
							if (newFwd.getDestinationAddress() == null) {
								if (oldFwd.getDestinationAddress() == null) {
									update = true;
								} else {
									update = true;

									this.fwdVector.removeElement(oldFwd);
									this.fwdVector.addElement(newFwd);
									break;
								}
							} else if ((!newFwd.getDestinationAddress().equals(
									oldFwd.getDestinationAddress()))
									&& (!newFwd.getDestinationAddress().equals(
											oldFwd.getDestinationAddress()
													+ "#"))) {
								update = true;

								this.fwdVector.removeElement(oldFwd);
								this.fwdVector.addElement(newFwd);
								break;
							}
						}
					}

					if ((!typeMatch) && (fwdInfo[i].isForwardingOn() == true)) {
						update = true;
						this.fwdVector.addElement(newFwd);
					}
				}
			}
		}

		if ((eventList != null) && (update == true)
				&& (this.forwardingInitialized)) {
			eventList.addElement(new TSEvent(39, this));
		}

		if (!this.forwardingInitialized)
			this.forwardingInitialized = true;
	}

	public Vector<TsapiAddressMonitor> getAddressObservers() {
		recreate();

		return new Vector<TsapiAddressMonitor>(this.addressMonitorThreads);
	}

	public Vector<TsapiTerminalMonitor> getTerminalObservers() {
		recreate();

		return new Vector<TsapiTerminalMonitor>(this.terminalMonitorThreads);
	}

	public Vector<TsapiRouteMonitor> getRouteObservers() {
		recreate();

		Vector<TsapiRouteMonitor> obs = new Vector<TsapiRouteMonitor>(1);
		synchronized (this.obsSync) {
			if (this.tsRouteCallback != null) {
				obs.addElement(this.tsRouteCallback);
			}
		}
		return obs;
	}

	void removeObservers(int cause, Object privateData, int xrefID) {
		recreate();

		if (xrefID != 0) {
			if (xrefID == this.monitorDeviceXRefID) {
				this.provider.deleteMonitor(this.monitorDeviceXRefID);
				this.monitorDeviceXRefID = 0;
			}
			if (xrefID == this.monitorCallsViaDeviceXRefID) {
				this.provider.deleteMonitor(this.monitorCallsViaDeviceXRefID);
				clearMonitorCallsViaDeviceAttributes();
			}
		}

		Vector<TsapiAddressMonitor> observers = new Vector<TsapiAddressMonitor>(this.addressMonitorThreads);
		for (int i = 0; i < observers.size(); i++) {
			removeAddressMonitor((TsapiAddressMonitor) observers.elementAt(i),
					cause, privateData);
		}
		Vector<TsapiTerminalMonitor> terminalObservers = new Vector<TsapiTerminalMonitor>(this.terminalMonitorThreads);
		for (int i = 0; i < terminalObservers.size(); i++) {
			removeTerminalMonitor(
					(TsapiTerminalMonitor) terminalObservers.elementAt(i),
					cause, privateData);
		}
		Vector<TsapiCallMonitor> callsViaAddressObservers = new Vector<TsapiCallMonitor>(
				this.callsViaAddressMonitorThreads);
		for (int i = 0; i < callsViaAddressObservers.size(); i++) {
			removeAddressCallMonitor(
					(TsapiCallMonitor) callsViaAddressObservers.elementAt(i),
					cause, privateData);
		}

		Vector<TsapiCallMonitor> callsAtAddressObservers = new Vector<TsapiCallMonitor>(
				this.callsAtAddressMonitorThreads);
		for (int i = 0; i < callsAtAddressObservers.size(); i++) {
			removeAddressCallMonitor(
					(TsapiCallMonitor) callsAtAddressObservers.elementAt(i),
					cause, privateData);
		}

		Vector<TsapiCallMonitor> callsAtTerminalObservers = new Vector<TsapiCallMonitor>(
				this.callsAtTerminalMonitorThreads);
		for (int i = 0; i < callsAtTerminalObservers.size(); i++) {
			removeTerminalCallMonitor(
					(TsapiCallMonitor) callsAtTerminalObservers.elementAt(i),
					cause, privateData);
		}

		this.internalDeviceMonitors.removeAllElements();

		stopMonitorForThisDevice();
	}

	boolean isMonitorSet() {
		recreate();

		return (this.monitorDeviceXRefID != 0)
				|| (this.monitorCallsViaDeviceXRefID != 0);
	}

	boolean isCallsViaDeviceMonitorSet() {
		recreate();

		return this.monitorCallsViaDeviceXRefID != 0;
	}

	boolean isPredictiveCallsViaDeviceMonitorSet() {
		recreate();

		return (this.monitorCallsViaDeviceXRefID != 0)
				&& (this.monitorPredictiveCallsViaDevice);
	}

	private void clearMonitorCallsViaDeviceAttributes() {
		this.monitorCallsViaDeviceXRefID = 0;
		this.monitorPredictiveCallsViaDevice = false;
	}

	void addConnection(TSConnection tsConn) {
		recreate();

		boolean doTerminalObservers = false;
		boolean doAddressObservers = false;

		if (tsConn.isTerminalConnection()) {
			if (isTerminal()) {
				synchronized (this.terminalConnections) {
					if (!this.terminalConnections.contains(tsConn)) {
						this.terminalConnections.addElement(tsConn);
						doTerminalObservers = true;
					}
				}
			}

		}

		if ((!this.provider.isLucent()) || (!tsConn.isTerminalConnection())) {
			synchronized (this.connections) {
				if (!this.connections.contains(tsConn)) {
					this.connections.addElement(tsConn);
					doAddressObservers = true;
				}
			}
		}

		if ((!doTerminalObservers) && (!doAddressObservers)) {
			return;
		}

		TSCall call = tsConn.getTSCall();
		call.addDeviceObservers(
				this,
				doTerminalObservers ? this.callsAtTerminalMonitorThreads : null,
				doAddressObservers ? this.callsAtAddressMonitorThreads : null,
				doAddressObservers ? this.callsViaAddressMonitorThreads : null,
				true);
	}

	CSTAConnectionID matchConn(CSTAConnectionID connID) {
		recreate();

		if (connID == null) {
			return null;
		}

		synchronized (this.terminalConnections) {
			for (int i = 0; i < this.terminalConnections.size(); i++) {
				TSConnection conn = (TSConnection) this.terminalConnections
						.elementAt(i);
				if (connID.equals(conn.connID)) {
					return conn.connID;
				}
			}
		}
		synchronized (this.connections) {
			for (int i = 0; i < this.connections.size(); i++) {
				TSConnection conn = (TSConnection) this.connections
						.elementAt(i);
				if (connID.equals(conn.connID)) {
					return conn.connID;
				}
			}
		}

		return null;
	}

	void removeConnection(TSConnection tsConn) {
		recreate();

		if (this.connections.removeElement(tsConn)) {
			TSCall call = tsConn.getTSCall();
			call.removeDefaultDeviceObservers(this, false);
		}
		if (this.terminalConnections.removeElement(tsConn)) {
			TSCall call = tsConn.getTSCall();
			call.removeDefaultDeviceObservers(this, true);
		}

		synchronized (this) {
			synchronized (this.devNameVector) {
				Vector<CSTAExtendedDeviceID> keys = new Vector<CSTAExtendedDeviceID>(this.devNameVector);
				for (int i = 0; i < keys.size(); i++) {
					CSTAExtendedDeviceID devID = (CSTAExtendedDeviceID) keys
							.elementAt(i);
					if ((devID.getDeviceIDStatus() == 0)
							&& ((devID.getDeviceIDType() == 70) || (devID
									.getDeviceIDType() == 71))) {
						if (this.devNameVector.size() == 1) {
							addName(new CSTAExtendedDeviceID("", (short) 70,
									(short) 1));
						}
						this.provider.deleteDeviceFromHash(devID.getDeviceID());
						this.devNameVector.removeElement(devID);
					}
				}
			}

		}

		testDelete();
	}

	String getAgentID() {
		recreate();

		if (this.provider.getCapabilities().getQueryDeviceInfo() != 0) {
			if (this.provider.isLucentV5()) {
				try {
					CSTAEvent event = this.provider.tsapi.queryDeviceInfo(
							getName(), null);
					if (event != null) {
						Object replyPriv = event.getPrivData();
						if ((replyPriv instanceof LucentV5QueryDeviceInfoConfEvent)) {
							LucentV5QueryDeviceInfoConfEvent V5devInfoConf = (LucentV5QueryDeviceInfoConfEvent) replyPriv;
							return V5devInfoConf.getAssociatedDevice();
						}

					}

				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else if (this.provider.isLucent()) {
				LucentQueryCallClassifier lqcc = null;
				lqcc = new LucentQueryCallClassifier();
				CSTAPrivate reqPriv = lqcc.makeTsapiPrivate();
				try {
					CSTAEvent event = this.provider.tsapi.queryDeviceInfo(
							getName(), reqPriv);
					if (event != null) {
						CSTAQueryDeviceInfoConfEvent devInfoConf = (CSTAQueryDeviceInfoConfEvent) event
								.getEvent();
						return devInfoConf.getDevice();
					}
					return null;
				} catch (TsapiUnableToSendException tue) {
					throw tue;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		return null;
	}

	synchronized void testDelete() {
		if ((!this.terminalMonitorThreads.isEmpty())
				|| (!this.addressMonitorThreads.isEmpty())
				|| (!this.callsViaAddressMonitorThreads.isEmpty())
				|| (!this.callsAtAddressMonitorThreads.isEmpty())
				|| (!this.callsAtTerminalMonitorThreads.isEmpty())
				|| (this.tsRouteCallback != null)
				|| (!this.tsACDVector.isEmpty())
				|| (!this.internalDeviceMonitors.isEmpty())
				|| (this.connections.size() > 0)
				|| (this.terminalConnections.size() > 0)
				|| (!this.callsWaitingForConnectPostConditions.isEmpty())) {
			return;
		}

		stopMonitorForThisDevice();

		if (this.refCount > 0) {
			return;
		}

		for (int i = 0; i < this.tsAgentTermVector.size(); i++) {
			TSAgent tsAgent = (TSAgent) this.tsAgentTermVector.elementAt(i);
			if ((tsAgent != null) && (tsAgent.isReferenced())) {
				return;
			}

		}

		realDelete();
	}

	private void realDelete() {
		if (this.curState.wasDeleteDone()) {
			log.info("Device "
					+ this
					+ " realDelete: already deleted - no further action taken, "
					+ this.provider);
			return;
		}

		if (!this.callsWaitingForConnectPostConditions.isEmpty()) {
			log.info("realDelete: " + this
					+ " Skipping because internal monitor is established");

			return;
		}

		log.info("Device " + this + " being deleted for " + this.provider);

		stopMonitorForThisDevice();

		if ((Tsapi.getTSDevicePerformanceOptimization() == true)
				&& (this.asyncInitializationComplete) && (!isDeviceExternal())) {
			log.info("NOT deleting " + this
					+ " due to TSDevice Performance Optimization");
			return;
		}

		setState(new TSDeviceStateBeingDeleted());

		this.provider.deleteInstanceOfDeviceFromHash(this);

		setState(new TSDeviceStateDeleted());
	}

	private void stopMonitorForThisDevice() {
		log.info("stopMonitorForThisDevice: Device " + this
				+ " about to consider DevMon stop (xref="
				+ this.monitorDeviceXRefID + " mcvdxref="
				+ this.monitorCallsViaDeviceXRefID + "), for " + this.provider);

		if (this.monitorDeviceXRefID != 0) {
			this.provider.deleteMonitor(this.monitorDeviceXRefID);
		}

		if (this.monitorCallsViaDeviceXRefID != 0) {
			this.provider.deleteMonitor(this.monitorCallsViaDeviceXRefID);
		}

		if (this.monitorDeviceXRefID != 0) {
			if (this.provider.getCapabilities().getMonitorStop() != 0) {
				if (this.provider.getState() != 18) {
					try {
						this.provider.tsapi.monitorStop(
								this.monitorDeviceXRefID, null, null);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				} else {
					log.info("stopMonitorForThisDevice: " + this
							+ " Skipping Monitor Stop because Provider is "
							+ "in SHUTDOWN state, monitorDeviceXRefID="
							+ this.monitorDeviceXRefID + " for "
							+ this.provider);
				}

			}

			this.monitorDeviceXRefID = 0;
		}

		if (this.monitorCallsViaDeviceXRefID != 0) {
			if (this.provider.getCapabilities().getMonitorStop() != 0) {
				if (this.provider.getState() != 18) {
					try {
						this.provider.tsapi.monitorStop(
								this.monitorCallsViaDeviceXRefID, null, null);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}

				} else {
					log.info("stopMonitorForThisDevice: " + this
							+ " Skipping Monitor Stop because Provider is "
							+ "in SHUTDOWN state, "
							+ "monitorCallsViaDeviceXRefID="
							+ this.monitorCallsViaDeviceXRefID + " for "
							+ this.provider);
				}

			}

			clearMonitorCallsViaDeviceAttributes();

			this.provider.removeAllCallsForDomain(this);
		}
	}

	public String referenced() {
		recreate();

		this.refCount += 1;
		if (!this.wasReferenced) {
			g_RefCnt += 1;
			this.wasReferenced = true;
		}

		return ((CSTAExtendedDeviceID) this.devNameVector.lastElement())
				.getDeviceID();
	}

	public void unreferenced() {
		recreate();

		this.refCount -= 1;
		testDelete();
	}

	public short getDeviceType() {
		recreate();

		if (!this.asyncInitializationComplete) {
			log.info("getDeviceType() for " + this);
		}

		waitForAsyncInitialization();

		return this.deviceType;
	}

	void setDeviceType(short _deviceType) {
		recreate();

		this.deviceType = _deviceType;
	}

	String getName() {
		recreate();

		return internalGetName();
	}

	private String internalGetName() {
		return ((CSTAExtendedDeviceID) this.devNameVector.lastElement())
				.getDeviceID();
	}

	Vector<CSTAExtendedDeviceID> getKeys() {
		recreate();

		return this.devNameVector;
	}

	void addName(CSTAExtendedDeviceID deviceID) {
		recreate();
		String devName = getName();

		synchronized (this.devNameVector) {
			if (!this.devNameVector.contains(deviceID)) {
				log.info("Renaming device " + this + " with name " + devName
						+ " to " + deviceID + " for " + this.provider);
				this.devNameVector.addElement(deviceID);
			}
		}
	}

	public boolean isTerminal() {
		recreate();

		if (!this.asyncInitializationComplete) {
			log.info("isTerminal() for " + this);
		}

		waitForAsyncInitialization();

		return this.isATerminal;
	}

	boolean isDeviceExternal() {
		recreate();

		if (!this.asyncInitializationComplete) {
			log.info("isDeviceExternal() for " + this);
		}

		waitForAsyncInitialization();

		return this.isExternal;
	}

	boolean wereChangesHeldPending() {
		recreate();

		return this.changesWereHeldPending;
	}

	public String toString() {
		return "TSDevice[" + internalGetName() + "]@"
				+ Integer.toHexString(hashCode());
	}

	protected void finalize() throws Throwable {
		try {
			log.info("TSDevice " + this + " - finalize() in state "
					+ this.curState);
			realDelete();
		} finally {
			super.finalize();
		}
	}

	void setAssociatedDevice(String associatedDevice) {
		recreate();

		this.associatedDevice = associatedDevice;
	}

	String getAssociatedDevice() {
		recreate();

		if (!this.asyncInitializationComplete) {
			log.info("getAssociatedDevice() for " + this);
		}

		waitForAsyncInitialization();

		return this.associatedDevice;
	}

	void setAssociatedClass(short associatedClass) {
		recreate();

		this.associatedClass = associatedClass;
	}

	short getAssociatedClass() {
		recreate();

		if (!this.asyncInitializationComplete) {
			log.info("getAssociatedClass() for " + this);
		}

		waitForAsyncInitialization();

		return this.associatedClass;
	}

	void setIsATerminal(boolean b) {
		recreate();

		this.isATerminal = b;
	}

	void setIsExternal(boolean b) {
		recreate();

		this.isExternal = b;
	}

	synchronized void notifyAsyncInitializationComplete() {
		recreate();

		log.info("Initialization complete for TSDevice " + this
				+ " - making values available - for " + this.provider);
		this.asyncInitializationComplete = true;
		notifyAll();
	}

	public String getDomainName() {
		return internalGetName();
	}

	public static boolean hasMixOfPublicAndPrivate(
			Vector<CSTAExtendedDeviceID> deviceList) {
		if (deviceList.size() <= 1) {
			return false;
		}
		CSTAExtendedDeviceID tempDevID = (CSTAExtendedDeviceID) deviceList
				.elementAt(0);
		String type = null;

		if (tempDevID.hasPrivateDeviceIDType())
			type = "PRIVATE";
		else if (tempDevID.hasPublicDeviceIDType())
			type = "PUBLIC";
		else {
			return true;
		}
		int i = 1;
		if (i < deviceList.size()) {
			tempDevID = (CSTAExtendedDeviceID) deviceList.elementAt(i);

			if ((type.equals("PUBLIC")) && (!tempDevID.hasPublicDeviceIDType()))
				return true;
			if ((type.equals("PRIVATE"))
					&& (!tempDevID.hasPrivateDeviceIDType())) {
				return true;
			}
			return true;
		}

		return false;
	}

	boolean isForExternalDeviceMatchingLocalExtensionNumber(
			CSTAExtendedDeviceID devIDOnCall) {
		if (hasMixOfPublicAndPrivate(this.devNameVector)) {
			StringBuffer tmpStrBuf = new StringBuffer();
			for (CSTAExtendedDeviceID tmpDevID : this.devNameVector) {
				tmpStrBuf.append(tmpDevID + " ");
			}
			log.info("TSDevice ["
					+ this
					+ "] has both public and private deviceID types. Here is a list : "
					+ tmpStrBuf.toString());
		}

		CSTAExtendedDeviceID lastDeviceExtDevID = (CSTAExtendedDeviceID) getKeys()
				.lastElement();

		if ((lastDeviceExtDevID.hasPrivateDeviceIDType())
				&& (devIDOnCall.hasPublicDeviceIDType())) {
			return true;
		}
		return false;
	}

	public CallControlForwarding[] createForwarding() {
		TsapiCallControlForwarding[] tsapiInstructions;
		synchronized (this.fwdVector) {
			tsapiInstructions = new TsapiCallControlForwarding[this.fwdVector
					.size()];
			this.fwdVector.copyInto(tsapiInstructions);
		}
		if (tsapiInstructions.length == 0) {
			return null;
		}
		CallControlForwarding[] instructions = new CallControlForwarding[tsapiInstructions.length];
		for (int i = 0; i < tsapiInstructions.length; i++) {
			if (tsapiInstructions[i].getFilter() == 1) {
				instructions[i] = new CallControlForwarding(
						tsapiInstructions[i].getDestinationAddress(),
						tsapiInstructions[i].getType());
			} else {
				instructions[i] = new CallControlForwarding(
						tsapiInstructions[i].getDestinationAddress(),
						tsapiInstructions[i].getType(),
						tsapiInstructions[i].getFilter() == 2);
			}

		}

		return instructions;
	}

	public Vector<CSTAExtendedDeviceID> getDevNameVector() {
		return this.devNameVector;
	}

	public void addToCallsWaitingForConnectPostConditions(TSCall call) {
		this.callsWaitingForConnectPostConditions.add(call);
	}

	public void removeFromCallsWaitingForConnectPostConditions(TSCall call) {
		this.callsWaitingForConnectPostConditions.remove(call);
	}
}