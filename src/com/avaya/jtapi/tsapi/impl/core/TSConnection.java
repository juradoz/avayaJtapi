package com.avaya.jtapi.tsapi.impl.core;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.ITsapiException;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiInvalidPartyException;
import com.avaya.jtapi.tsapi.TsapiInvalidStateException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException;
import com.avaya.jtapi.tsapi.TsapiResourceUnavailableException;
import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningHold;
import com.avaya.jtapi.tsapi.csta1.LucentSelectiveListeningRetrieve;
import com.avaya.jtapi.tsapi.csta1.LucentSendDTMFTone;
import com.avaya.jtapi.tsapi.csta1.LucentV5SendDTMFTone;
import com.avaya.jtapi.tsapi.impl.TsapiConnCapabilities;
import com.avaya.jtapi.tsapi.impl.TsapiCreateObject;
import com.avaya.jtapi.tsapi.impl.TsapiTermConnCapabilities;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

public final class TSConnection {
	private static Logger log = Logger.getLogger(TSConnection.class);
	TSProviderImpl provider;
	int connState;
	int termConnState;
	boolean isTermConn = false;
	TSConnection connection;
	Vector<TSConnection> termConns;
	Vector<TSConnection> staleTermConns;
	boolean haveNetworkReached = false;
	CSTAConnectionID connID;
	TSCall call;
	TSDevice device;
	Object replyTermConnPriv = null;
	Object replyConnPriv = null;

	boolean constructed = false;

	TSConnection acdManagerConn = null;
	Vector<TSConnection> acdConns = null;

	TSTrunk trunk = null;

	private TSCallObjectAge my_age = new TSCallObjectAge();

	private boolean doNotExpectConnectionClearedEvent = false;

	TSConnection(TSProviderImpl _provider, CSTAConnectionID _connID,
			TSDevice _device, boolean _wantTermConn) {
		constructed = false;
		provider = _provider;
		connID = _connID;
		device = _device;
		connState = 80;
		termConnState = 96;
		acdConns = new Vector<TSConnection>();

		isTermConn = _wantTermConn;

		if (isTermConn) {
			if (device != null) {
				isTermConn = device.isTerminal();
			} else {
				isTermConn = false;
			}
		}

		if (connID != null) {
			call = provider.createCall(connID.getCallID());
			if (call.getTSState() == 34) {
				provider.dumpCall(connID.getCallID());
				call = provider.createCall(connID.getCallID());
			}
		}

		StringBuffer connForProviderString = new StringBuffer();

		connForProviderString.append(this).append(" for ").append(provider);

		if (provider.isLucent()) {
			if (isTermConn) {
				log.info("Constructing Lucent termConn "
						+ connForProviderString.toString());
			} else {
				log.info("Constructing Lucent conn "
						+ connForProviderString.toString());
			}

		} else {
			log.info("Constructing conn " + connForProviderString.toString());
		}
	}

	void addACDConns(TSConnection acdConn) {
		synchronized (acdConns) {
			if (!acdConns.contains(acdConn)) {
				acdConns.addElement(acdConn);
			}
		}
	}

	private void addMyCustomStringCallID(StringBuffer text) {
		if (connID == null) {
			text.append((call == null) ? "-" : Integer.toString(call
					.getCallID()));
		} else {
			text.append((connID.getCallID() == 0) ? "-" : Integer
					.toString(connID.getCallID()));
		}
	}

	private void addMyCustomStringConnectionID(StringBuffer text) {
		text.append("(");
		addMyCustomStringCallID(text);
		text.append(",");
		addMyCustomStringDeviceID(text);
		text.append(")");
	}

	private void addMyCustomStringDeviceID(StringBuffer text) {
		if (connID == null) {
			text.append((device == null) ? "-" : device.getName());
		} else {
			text.append((connID.getDeviceID() == null) ? "-" : connID
					.getDeviceID());
		}
	}

	void addTerminalConnection(TSConnection termConn, Vector<TSEvent> eventList) {
		if (termConns == null) {
			termConns = new Vector<TSConnection>();
			termConns.addElement(termConn);
			staleTermConns = new Vector<TSConnection>();
			if (call == null) {
				call = termConn.call;
				device.addConnection(this);
				call.addConnection(this, eventList);
			}
			connID = null;
		} else {
			synchronized (termConns) {
				if (!termConns.contains(termConn)) {
					termConns.addElement(termConn);
				}
			}
		}

		termConn.connection = this;
	}

	public void answer(CSTAPrivate reqTermConnPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getAnswerCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (call.updateObject()) {
			int state = getTSTermConnState();
			if ((state != 65) && (state != 69)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(this, false), 5, state,
						"terminal connection not ringing");
			}

		}

		ConfHandler handler = new TalkingConfHandler(this, 4);
		try {
			provider.tsapi.answerCall(connID, reqTermConnPriv, handler);
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			if (e.getErrorType() == 2) {
				switch (e.getErrorCode()) {
				case 13:
				case 28:
					log.info("Conn " + this + " answer UniversalFailure "
							+ e.getErrorCode() + " requires snapshot of "
							+ call + " for " + provider);
					call.updateSuspiciousObject();
				}

			}

			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"answerCall failure");
			}
			throw new TsapiPlatformException(4, 0, "answerCall failure");
		}
	}

	void delete() {
		log.info("Connection object= " + this + " being deleted" + " for "
				+ provider);

		if (connID != null) {
			provider.deleteConnectionFromHash(connID);
			provider.addConnectionToSaveHash(this);
		}
		if (staleTermConns == null) {
			return;
		}
		synchronized (staleTermConns) {
			for (int i = 0; i < staleTermConns.size(); ++i) {
				((TSConnection) staleTermConns.elementAt(i)).delete();
			}
		}
	}

	public void disconnect(CSTAPrivate reqConnPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getClearConnection() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (call.updateObject()) {
			int state = getTSConnState();
			if ((state != 49) && (state != 50) && (state != 51)
					&& (state != 53) && (state != 54)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(this, true), 2, state,
						"connection not in acceptable state");
			}

		}

		DisconnectedConfHandler handler = new DisconnectedConfHandler(this, 10);
		try {
			if ((provider.isLucent()) && (termConns != null)) {
				Vector<TSConnection> tcArray = new Vector<TSConnection>(termConns);

				handler.handleIt = false;
				for (int i = 0; i < tcArray.size(); ++i) {
					TSConnection tc = (TSConnection) tcArray.elementAt(i);
					if (i == tcArray.size() - 1) {
						handler.handleIt = true;
					}
					provider.tsapi.clearConnection(tc.connID, reqConnPriv,
							handler);
				}
			} else {
				provider.tsapi.clearConnection(connID, reqConnPriv, handler);
			}
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			if (e.getErrorType() == 2) {
				switch (e.getErrorCode()) {
				case 24:
				case 27:
					log.info("Conn " + this + ": clearConnection "
							+ "Universal Failure with error "
							+ e.getErrorCode() + " requires snapshot of "
							+ call + " for " + provider);

					call.updateSuspiciousObject();
				}

			}

			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"clearConnection failure");
			}
			throw new TsapiPlatformException(4, 0, "clearConnection failure");
		}
	}

	void dump(String indent) {
		log.trace(indent + "***** CONNECTION DUMP *****");
		log.trace(indent + "TSConnection: " + this);
		log.trace(indent + "TSConnection ID: " + connID);
		log
				.trace(indent + "TSConnection is terminal connection? "
						+ isTermConn);
		log.trace(indent + "TSConnection age: " + my_age);
		log.trace(indent + "TSConnection conn state: " + connState);
		log.trace(indent + "TSConnection term conn state: " + termConnState);
		if (termConns != null) {
			log.trace(indent + "TSConnection terminal connections: ");
			synchronized (termConns) {
				for (int i = 0; i < termConns.size(); ++i) {
					TSConnection conn = (TSConnection) termConns.elementAt(i);
					conn.dump(indent + " ");
				}
			}
		}
		if (staleTermConns != null) {
			log.trace(indent + "TSConnection stale terminal connections: ");
			synchronized (staleTermConns) {
				for (int i = 0; i < staleTermConns.size(); ++i) {
					TSConnection conn = (TSConnection) staleTermConns
							.elementAt(i);
					conn.dump(indent + " ");
				}
			}
		}
		if (connection != null) {
			log.trace(indent + "TSConnection connection: " + connection);
		}
		if (trunk != null) {
			log.trace(indent + "TSTrunk trunk: " + trunk);
		}
		log.trace(indent + "***** CONNECTION DUMP END *****");
	}

	synchronized void finishConstruction(TSDevice _connectionAddress,
			Vector<TSEvent> eventList) {
		boolean found = true;
		if ((isTermConn) && (provider.isLucent())
				&& (_connectionAddress != null)) {
			device.addConnection(this);
			Vector<TSConnection> connVector = new Vector<TSConnection>(call.getConnections());
			TSConnection addressConnection = null;
			found = false;
			for (int i = 0; i < connVector.size(); ++i) {
				addressConnection = (TSConnection) connVector.elementAt(i);
				if (addressConnection.getTSDevice() != _connectionAddress) {
					continue;
				}
				connection = addressConnection;
				connection.addTerminalConnection(this, eventList);
				found = true;
				break;
			}

		} else if (call != null) {
			device.addConnection(this);
			call.addConnection(this, eventList);
		}

		constructed = true;
		super.notifyAll();

		if (!found) {
			connection = provider.createConnection(null, _connectionAddress,
					null);
			connection.addTerminalConnection(this, eventList);
			if (eventList != null) {
				eventList.addElement(new TSEvent(6, connection));
			}
		}
		if (eventList == null) {
			return;
		}

		if ((!isTermConn) || (!provider.isLucent())) {
			eventList.addElement(new TSEvent(6, this));
		}
		if (isTermConn) {
			eventList.addElement(new TSEvent(13, this));
		}
	}

	public void generateDtmf(String digits) throws TsapiInvalidStateException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		generateDtmf(digits, 0, 0);
	}

	public void generateDtmf(String digits, int toneDuration, int pauseDuration)
			throws TsapiInvalidStateException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		if (!provider.isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		LucentSendDTMFTone dtmf;
		if (provider.isLucentV5()) {
			dtmf = new LucentV5SendDTMFTone(connID, null, digits, toneDuration,
					pauseDuration);
		} else {
			dtmf = new LucentSendDTMFTone(connID, null, digits, toneDuration,
					pauseDuration);
		}
		try {
			provider.sendPrivateData(dtmf.makeTsapiPrivate());
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"sendPrivateData failure");
			}
			throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
		}
	}

	public Vector<TSConnection> getACDConns() {
		return acdConns;
	}

	public TSConnection getACDManagerConn() {
		return acdManagerConn;
	}

	public TSCall getCall() {
		return call;
	}

	public int getCallControlConnectionState() {
		call.updateObject();

		return getCallControlConnState();
	}

	int getCallControlConnState() {
		if ((isTermConn) && (connection != null)) {
			return connection.getCallControlConnState();
		}

		return connState;
	}

	int getCallControlTermConnState() {
		return termConnState;
	}

	public int getCallControlTerminalConnectionState() {
		call.updateObject();
		return getCallControlTermConnState();
	}

	public int getConnectionState() {
		call.updateObject();

		return getTSConnState();
	}

	public CSTAConnectionID getConnID() {
		synchronized (this) {
			if (connID != null) {
				return connID;
			}
		}
		if (termConns != null) {
			TSConnection tc = null;
			CSTAConnectionID tcConnID = null;
			synchronized (termConns) {
				for (int i = 0; i < termConns.size(); ++i) {
					tc = (TSConnection) termConns.elementAt(i);
					try {
						tcConnID = tc.getConnID();
					} catch (TsapiPlatformException e) {
						log.error("Ignoring exception: " + e);
					}
					if (tcConnID != null) {
						return tcConnID;
					}
				}
			}
		}
		if (staleTermConns != null) {
			TSConnection tc = null;
			CSTAConnectionID tcConnID = null;
			synchronized (staleTermConns) {
				for (int i = 0; i < staleTermConns.size(); ++i) {
					tc = (TSConnection) staleTermConns.elementAt(i);
					try {
						tcConnID = tc.getConnID();
					} catch (TsapiPlatformException e) {
						log.error("Ignoring exception: " + e);
					}
					if (tcConnID != null) {
						return tcConnID;
					}
				}
			}
		}
		throw new TsapiPlatformException(4, 0, "no connection id found!");
	}

	public Object getConnPrivateData() {
		if (replyConnPriv instanceof CSTAPrivate) {
			return replyConnPriv;
		}
		return null;
	}

	private String getMyCustomString() {
		StringBuffer accumulator = new StringBuffer();

		if (isTermConn) {
			accumulator.append("termConn:");
			addMyCustomStringConnectionID(accumulator);
		} else {
			accumulator.append("conn:");
			addMyCustomStringConnectionID(accumulator);
		}

		return accumulator.toString();
	}

	void getSnapshot(Vector<TSEvent> eventList) {
		getSnapshot(eventList, true);
	}

	void getSnapshot(Vector<TSEvent> eventList, boolean includeCreated) {
		if ((!isTermConn) || (!provider.isLucent())) {
			if (includeCreated) {
				eventList.addElement(new TSEvent(6, this));
			}
			switch (connState) {
			case 83:
				eventList.addElement(new TSEvent(9, this));
				eventList.addElement(new TSEvent(26, this));
				break;
			case 88:
				eventList.addElement(new TSEvent(7, this));
				eventList.addElement(new TSEvent(21, this));
				break;
			case 86:
				eventList.addElement(new TSEvent(7, this));
				eventList.addElement(new TSEvent(22, this));
				break;
			case 87:
				eventList.addElement(new TSEvent(7, this));
				eventList.addElement(new TSEvent(23, this));
				break;
			case 84:
				eventList.addElement(new TSEvent(7, this));
				eventList.addElement(new TSEvent(24, this));
				break;
			case 89:
				eventList.addElement(new TSEvent(10, this));
				eventList.addElement(new TSEvent(27, this));
				break;
			case 90:
				eventList.addElement(new TSEvent(11, this));
				eventList.addElement(new TSEvent(28, this));
				break;
			case 82:
				eventList.addElement(new TSEvent(8, this));
				eventList.addElement(new TSEvent(56, this));
				eventList.addElement(new TSEvent(25, this));
				break;
			case 91:
				eventList.addElement(new TSEvent(12, this));
				eventList.addElement(new TSEvent(29, this));
				break;
			case 81:
				eventList.addElement(new TSEvent(8, this));
				eventList.addElement(new TSEvent(56, this));
				eventList.addElement(new TSEvent(19, this));
				break;
			case 85:
				eventList.addElement(new TSEvent(7, this));
				eventList.addElement(new TSEvent(20, this));
			}

			if ((provider.isLucent()) && (termConns != null)) {
				synchronized (termConns) {
					for (int i = 0; i < termConns.size(); ++i) {
						((TSConnection) termConns.elementAt(i)).getSnapshot(
								eventList, includeCreated);
					}
				}
			}
		}
		if (!isTermConn) {
			return;
		}
		if (includeCreated) {
			eventList.addElement(new TSEvent(13, this));
		}
		switch (termConnState) {
		case 98:
			eventList.addElement(new TSEvent(14, this));
			eventList.addElement(new TSEvent(30, this));
			break;
		case 99:
			eventList.addElement(new TSEvent(14, this));
			eventList.addElement(new TSEvent(31, this));
			break;
		case 102:
			eventList.addElement(new TSEvent(17, this));
			eventList.addElement(new TSEvent(34, this));
			break;
		case 97:
			eventList.addElement(new TSEvent(15, this));
			eventList.addElement(new TSEvent(35, this));
			break;
		case 100:
			eventList.addElement(new TSEvent(16, this));
			eventList.addElement(new TSEvent(32, this));
			break;
		case 101:
			eventList.addElement(new TSEvent(16, this));
			eventList.addElement(new TSEvent(33, this));
			break;
		case 103:
			eventList.addElement(new TSEvent(18, this));
			eventList.addElement(new TSEvent(36, this));
		}
	}

	public Object getTermConnPrivateData() {
		if (replyTermConnPriv instanceof CSTAPrivate) {
			return replyTermConnPriv;
		}
		return null;
	}

	Vector<TSConnection> getTermConns() {
		if ((provider.isLucent()) && (termConns != null)) {
			return termConns;
		}

		Vector<TSConnection> cv = new Vector<TSConnection>();

		if (isTermConn) {
			cv.addElement(this);
		}

		return cv;
	}

	public int getTerminalConnectionState() {
		call.updateObject();

		return getTSTermConnState();
	}

	public TsapiConnCapabilities getTsapiConnCapabilities() {
		return provider.getTsapiConnCapabilities();
	}

	public TsapiTermConnCapabilities getTsapiTermConnCapabilities() {
		return provider.getTsapiTermConnCapabilities();
	}

	public TSCall getTSCall() {
		return call;
	}

	TSConnection getTSConn() {
		if ((provider.isLucent()) && (isTermConn)) {
			return connection;
		}

		return this;
	}

	public TSConnection getTSConnection() {
		call.updateObject();
		return getTSConn();
	}

	int getTSConnState() {
		int connectionState;
		if ((isTermConn) && (connection != null)) {
			connectionState = connection.getCallControlConnState();
		} else {
			connectionState = connState;
		}
		switch (connectionState) {
		case 80:
			return 48;
		case 81:
		case 82:
			return 49;
		case 83:
			return 50;
		case 84:
		case 85:
		case 86:
		case 87:
		case 88:
			return 51;
		case 89:
			return 52;
		case 90:
			return 53;
		case 91:
		}
		return 54;
	}

	public TSDevice getTSDevice() {
		return device;
	}

	public TSProviderImpl getTSProviderImpl() {
		return provider;
	}

	public Vector<TSConnection> getTSTermConns() {
		if (getConnectionState() == 52) {
			return null;
		}

		return getTermConns();
	}

	int getTSTermConnState() {
		switch (termConnState) {
		case 96:
			return 64;
		case 97:
			return 65;
		case 98:
		case 99:
			return 67;
		case 100:
		case 101:
			return 66;
		case 102:
			return 68;
		case 103:
		}
		return 69;
	}

	public TSTrunk getTSTrunk() {
		return trunk;
	}

	public void hold(CSTAPrivate reqTermConnPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getHoldCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (call.updateObject()) {
			int state = getCallControlTermConnState();
			if ((state != 98) && (state != 103)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(this, false), 5, state,
						"terminal connection not talking");
			}

		}

		ConfHandler handler = new HoldConfHandler(this);
		try {
			provider.tsapi.holdCall(connID, false, reqTermConnPriv, handler);
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			if (e.getErrorType() == 2) {
				switch (e.getErrorCode()) {
				case 13:
				case 24:
					log.info("Conn " + this + " hold UniversalFailure "
							+ e.getErrorCode() + " requires snapshot of "
							+ call + " for " + provider);
					call.updateSuspiciousObject();
				}

			}

			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"holdCall failure");
			}
			throw new TsapiPlatformException(4, 0, "holdCall failure");
		}
	}

	public boolean isDoNotExpectConnectionClearedEvent() {
		return doNotExpectConnectionClearedEvent;
	}

	boolean isTerminalConnection() {
		return isTermConn;
	}

	public void join(CSTAPrivate reqTermConnPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (!provider.isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (call.updateObject()) {
			int state = getCallControlTermConnState();
			if ((state != 100) && (state != 103)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(this, false), 5, state,
						"terminal connection not bridged");
			}

		}

		ConfHandler handler = new TalkingConfHandler(this, 4);
		try {
			provider.tsapi.answerCall(connID, reqTermConnPriv, handler);
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			if (e.getErrorType() == 2) {
				switch (e.getErrorCode()) {
				case 13:
				case 28:
					log.info("Conn " + this + " join UniversalFailure "
							+ e.getErrorCode() + " requires snapshot of "
							+ call + " for " + provider);
					call.updateSuspiciousObject();
				}

			}

			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"join failure");
			}
			throw new TsapiPlatformException(4, 0, "join failure");
		}
	}

	public void leave(CSTAPrivate reqTermConnPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (!provider.isLucent()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (call.updateObject()) {
			int state = getCallControlTermConnState();
			if ((state != 98) && (state != 103)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(this, false), 5, state,
						"terminal connection not talking");
			}

		}

		ConfHandler handler = new BridgedConfHandler(this);
		try {
			provider.tsapi.clearConnection(connID, reqTermConnPriv, handler);
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			if (e.getErrorType() == 2) {
				switch (e.getErrorCode()) {
				case 24:
				case 27:
					log.info("Conn " + this + ": clearConnection "
							+ "Universal Failure with error "
							+ e.getErrorCode() + " requires snapshot of "
							+ call + " for " + provider);

					call.updateSuspiciousObject();
				}

			}

			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"clearConnection failure");
			}
			throw new TsapiPlatformException(4, 0, "clearConnection failure");
		}
	}

	public void listenHold(TSConnection partyToHold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		if (!provider.isLucentV5()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (termConns != null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"subject Connection contains TerminalConnections");
		}
		if (connID == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"subject connID is null");
		}

		boolean allParties = true;
		CSTAConnectionID selectedParty = null;

		if (partyToHold != null) {
			selectedParty = partyToHold.connID;
			if (selectedParty == null) {
				throw new TsapiInvalidArgumentException(3, 0,
						"partyToHold connID is null");
			}
			allParties = false;
		}

		try {
			LucentSelectiveListeningHold slh = new LucentSelectiveListeningHold(
					connID, allParties, selectedParty);

			provider.sendPrivateData(slh.makeTsapiPrivate());
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"listenHold failure");
			}
			throw new TsapiPlatformException(4, 0, "listenHold failure");
		}
	}

	public void listenUnhold(TSConnection partyToUnhold)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException {
		if (!provider.isLucentV5()) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (connID == null) {
			throw new TsapiInvalidArgumentException(3, 0,
					"subject connID is null");
		}

		boolean allParties = true;
		CSTAConnectionID selectedParty = null;

		if (partyToUnhold != null) {
			selectedParty = partyToUnhold.connID;
			if (selectedParty == null) {
				throw new TsapiInvalidArgumentException(3, 0,
						"partyToUnhold connID is null");
			}
			allParties = false;
		}

		try {
			LucentSelectiveListeningRetrieve slr = new LucentSelectiveListeningRetrieve(
					connID, allParties, selectedParty);

			provider.sendPrivateData(slr.makeTsapiPrivate());
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"listenUnhold failure");
			}
			throw new TsapiPlatformException(4, 0, "listenUnhold failure");
		}
	}

	public TSConnection redirect(String destinationAddress,
			CSTAPrivate reqConnPriv) throws TsapiPrivilegeViolationException,
			TsapiInvalidPartyException, TsapiInvalidStateException,
			TsapiResourceUnavailableException, TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getDeflectCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (call.updateObject()) {
			int state = getCallControlConnState();
			if ((state != 81) && (state != 83) && (state != 91)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(this, true), 2, state,
						"connection not offering or alerting");
			}

		}

		ConfHandler handler = new DisconnectedConfHandler(this, 16);
		try {
			provider.tsapi.deflectCall(getConnID(), destinationAddress,
					reqConnPriv, handler);
		} catch (TsapiInvalidStateException e) {
			throw e;
		} catch (TsapiInvalidPartyException e) {
			throw e;
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"deflectCall failure");
			}
			throw new TsapiPlatformException(4, 0, "deflectCall failure");
		}

		return null;
	}

	void removeTerminalConnection(TSConnection termConn,
			Vector<TSEvent> eventList) {
		if (termConns == null) {
			return;
		}
		if (!termConns.removeElement(termConn)) {
			return;
		}
		synchronized (staleTermConns) {
			if (!staleTermConns.contains(termConn)) {
				staleTermConns.addElement(termConn);
			}
		}

		if (termConns.size() != 0) {
			return;
		}
		setConnectionState(89, eventList);
	}

	public Object sendPrivateData(CSTAPrivate data) {
		try {
			return provider.sendPrivateData(data);
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"sendPrivateData failure");
			}
			throw new TsapiPlatformException(3, 0, "sendPrivateData failure");
		}
	}

	void setACDManagerConn(TSConnection _acdManagerConn) {
		if (_acdManagerConn == null) {
			return;
		}
		acdManagerConn = _acdManagerConn;
	}

	synchronized void setCall(TSCall newCall) {
		call = newCall;
		if (termConns == null) {
			return;
		}
		for (int i = 0; i < termConns.size(); ++i) {
			TSConnection tc = (TSConnection) termConns.elementAt(i);
			tc.setCall(newCall);
		}
	}

	void setConnectionState(int _connState, Vector<TSEvent> eventList) {
		if ((isTermConn) && (provider.isLucent())) {
			if (connection != null) {
				connection.setConnectionState(_connState, eventList);
			}
			return;
		}

		int oldCoreState = getTSConnState();

		synchronized (this) {
			if ((haveNetworkReached) && (_connState == 83)) {
				_connState = 87;
			} else if ((_connState == 82) && (device.getDeviceType() == 1)) {
				_connState = 83;
			}

			if ((connState == _connState) || (connState == 89)) {
				return;
			}
			connState = _connState;
		}

		switch (connState) {
		case 83:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 50) {
				eventList.addElement(new TSEvent(9, this));
			}
			eventList.addElement(new TSEvent(26, this));
			break;
		case 88:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 51) {
				eventList.addElement(new TSEvent(7, this));
			}
			eventList.addElement(new TSEvent(21, this));
			break;
		case 86:
			if (eventList != null) {
				if (oldCoreState != 51) {
					eventList.addElement(new TSEvent(7, this));
				}
				eventList.addElement(new TSEvent(22, this));
			}
			synchronized (this) {
				haveNetworkReached = true;
			}
			break;
		case 87:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 51) {
				eventList.addElement(new TSEvent(7, this));
			}
			eventList.addElement(new TSEvent(23, this));
			break;
		case 84:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 51) {
				eventList.addElement(new TSEvent(7, this));
			}
			eventList.addElement(new TSEvent(24, this));

			if (provider.getCapabilities().getOriginatedEvent() != 0) {
				return;
			}
			setConnectionState(88, eventList);
			break;
		case 89:
			synchronized (this) {
				if (trunk != null) {
					call.removeTrunk(trunk, eventList);
					trunk = null;
				}

			}

			if (termConns != null) {
				Vector<TSConnection> conn = new Vector<TSConnection>(termConns);
				int i;
				for (i = 0; i < conn.size(); ++i) {
					((TSConnection) conn.elementAt(i)).setTermConnState(102,
							eventList);
				}

			} else {
				setTermConnState(102, eventList);
			}

			if (acdManagerConn != null) {
				TSConnection acdMgrConn = acdManagerConn;
				Vector<TSConnection> acdConns = acdMgrConn.getACDConns();
				int j;
				for (j = 0; j < acdConns.size(); ++j) {
					((TSConnection) acdConns.elementAt(j))
							.setACDManagerConn(null);
					((TSConnection) acdConns.elementAt(j)).setConnectionState(
							89, eventList);
				}
				acdMgrConn.setConnectionState(89, eventList);
			}

			if (eventList != null) {
				if (oldCoreState != 52) {
					eventList.addElement(new TSEvent(10, this));
				}
				eventList.addElement(new TSEvent(27, this));
			}
			device.removeConnection(this);
			call.removeConnection(this, eventList);
			break;
		case 90:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 53) {
				eventList.addElement(new TSEvent(11, this));
			}
			eventList.addElement(new TSEvent(28, this));
			break;
		case 82:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 49) {
				eventList.addElement(new TSEvent(8, this));
				eventList.addElement(new TSEvent(56, this));
			}
			eventList.addElement(new TSEvent(25, this));
			break;
		case 91:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 54) {
				eventList.addElement(new TSEvent(12, this));
			}
			eventList.addElement(new TSEvent(29, this));
			break;
		case 81:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 49) {
				eventList.addElement(new TSEvent(8, this));
				eventList.addElement(new TSEvent(56, this));
			}
			eventList.addElement(new TSEvent(19, this));
			break;
		case 85:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 51) {
				eventList.addElement(new TSEvent(7, this));
			}
			eventList.addElement(new TSEvent(20, this));
		}
	}

	synchronized void setConnID(CSTAConnectionID newConnID) {
		if ((newConnID != null) && (newConnID.equals(connID))) {
			return;
		}

		if ((isTermConn) && (newConnID == null)) {
			return;
		}

		provider.deleteConnectionFromHash(connID);

		CSTAConnectionID oldConnID = connID;
		connID = newConnID;

		TSConnection saveConn = provider.addConnectionToHash(this);

		if (saveConn == null) {
			return;
		}
		if (oldConnID != null) {
			saveConn.connID = oldConnID;
			provider.addConnectionToHash(saveConn);
		} else {
			log
					.info("Replaced an older connection with a Conn that has null Conn ID. Not restoring the older connection.");

			log.trace("Dumping call (" + call + "):");
			call.dump("   ");
			log.trace("Dumping conn (" + this + "):");
			dump("   ");
			log.trace("Dumping provider (" + provider + "):");
			provider.dump("   ");
		}
	}

	public void setDoNotExpectConnectionClearedEvent(
			boolean connBelongToDifferentDeviceIDType) {
		doNotExpectConnectionClearedEvent = connBelongToDifferentDeviceIDType;
		log.info("Conn " + this
				+ ", setting flag 'connBelongToDifferentDeviceIDType'");
	}

	void setStateFromLocalConnState(int localCallState) {
		switch (localCallState) {
		case 1:
			setConnectionState(84, null);
			setTermConnState(98, null);
			break;
		case 2:
			setConnectionState(83, null);
			setTermConnState(97, null);
			break;
		case 3:
			setConnectionState(88, null);
			setTermConnState(98, null);
			break;
		case 4:
			setConnectionState(88, null);
			setTermConnState(99, null);
			break;
		case 5:
			setConnectionState(82, null);
			break;
		case 6:
			setConnectionState(90, null);
			setTermConnState(102, null);
			break;
		case 0:
			if (!provider.isLucent()) {
				// break label195;
				break;
			}
			log.info("NULL localCallState implies BRIDGED for " + this);
			setConnectionState(88, null);
			setTermConnState(100, null);
			break;
		case -1:
		default:
			setConnectionState(91, null);
			setTermConnState(103, null);
		}
	}

	void setTermConnState(int _termConnState, Vector<TSEvent> eventList) {
		if (!isTermConn) {
			return;
		}

		int oldCoreState = getTSTermConnState();

		synchronized (this) {
			if ((haveNetworkReached) && (_termConnState == 97)) {
				_termConnState = 98;
			}

			if ((termConnState == _termConnState) || (termConnState == 102)) {
				return;
			}

			termConnState = _termConnState;
		}

		switch (termConnState) {
		case 98:
			if (eventList != null) {
				if (oldCoreState != 67) {
					eventList.addElement(new TSEvent(14, this));
				}
				eventList.addElement(new TSEvent(30, this));
			}

			if ((connection == null) || (connection.termConns == null)) {
				return;
			}
			Vector<TSConnection> conns = new Vector<TSConnection>(connection.termConns);
			for (int i = 0; i < conns.size(); ++i) {
				TSConnection conn = (TSConnection) conns.elementAt(i);
				if (conn == this) {
					continue;
				}
				if (conn.termConnState != 97) {
					continue;
				}
				conn.setTermConnState(100, eventList);
			}

			break;
		case 99:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 67) {
				eventList.addElement(new TSEvent(14, this));
			}
			eventList.addElement(new TSEvent(31, this));
			break;
		case 102:
			if (eventList != null) {
				if (oldCoreState != 68) {
					eventList.addElement(new TSEvent(17, this));
				}
				eventList.addElement(new TSEvent(34, this));
			}
			device.removeConnection(this);
			if (connection != null) {
				connection.removeTerminalConnection(this, eventList);
				return;
			}

			call.removeConnection(this, eventList);

			break;
		case 97:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 65) {
				eventList.addElement(new TSEvent(15, this));
			}
			eventList.addElement(new TSEvent(35, this));
			break;
		case 100:
			boolean okToBridge = false;
			int i = 0;
			if ((connection != null) && (connection.termConns != null)) {
				synchronized (connection.termConns) {
					if (connection.termConns.size() == 1) {
						i = 1;
					} else {
						for (i = 0; i < connection.termConns.size(); ++i) {
							TSConnection conn = (TSConnection) connection.termConns
									.elementAt(i);
							if (conn == this) {
								continue;
							}
							if ((conn.termConnState != 98)
									&& (conn.termConnState != 103)) {
								continue;
							}
							okToBridge = true;
							break;
						}
					}
				}

			}

			if (okToBridge) {
				if (eventList == null) {
					return;
				}
				if (oldCoreState != 66) {
					eventList.addElement(new TSEvent(16, this));
				}
				eventList.addElement(new TSEvent(32, this));
				return;
			}

			if (i != 0) {
				setTermConnState(97, eventList);
				return;
			}
			if (connection == null) {
				return;
			}
			connection.setConnectionState(89, eventList);
			break;
		case 101:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 66) {
				eventList.addElement(new TSEvent(16, this));
			}
			eventList.addElement(new TSEvent(33, this));
			break;
		case 103:
			if (eventList == null) {
				return;
			}
			if (oldCoreState != 69) {
				eventList.addElement(new TSEvent(18, this));
			}
			eventList.addElement(new TSEvent(36, this));
		}
	}

	void setTerminalConnection() {
		if (!provider.isLucent()) {
			isTermConn = true;
		}
	}

	public synchronized void setTrunk(TSTrunk _trunk) {
		if (_trunk == null) {
			return;
		}
		trunk = _trunk;
	}

	public String toString() {
		return "TSConnection[" + getMyCustomString() + "]@"
				+ Integer.toHexString(super.hashCode());
	}

	public void unhold(CSTAPrivate reqTermConnPriv)
			throws TsapiPrivilegeViolationException,
			TsapiInvalidStateException, TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException {
		if (provider.getCapabilities().getRetrieveCall() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}
		if (call.updateObject()) {
			int state = getCallControlTermConnState();
			if ((state != 99) && (state != 103)) {
				throw new TsapiInvalidStateException(3, 0, TsapiCreateObject
						.getTsapiObject(this, false), 5, state,
						"terminal connection not held");
			}

		}

		ConfHandler handler = new TalkingConfHandler(this, 42);
		try {
			provider.tsapi.retrieveCall(connID, reqTermConnPriv, handler);
		} catch (TsapiPrivilegeViolationException e) {
			throw e;
		} catch (TsapiResourceUnavailableException e) {
			throw e;
		} catch (TsapiPlatformException e) {
			if (e.getErrorType() == 2) {
				switch (e.getErrorCode()) {
				case 13:
				case 24:
					log.info("Conn " + this + " unhold UniversalFailure "
							+ e.getErrorCode() + " requires snapshot of "
							+ call + " for " + provider);
					call.updateSuspiciousObject();
				}

			}

			throw e;
		} catch (Exception e) {
			if (e instanceof ITsapiException) {
				throw new TsapiPlatformException(((ITsapiException) e)
						.getErrorType(), ((ITsapiException) e).getErrorCode(),
						"retrieveCall failure");
			}
			throw new TsapiPlatformException(4, 0, "retrieveCall failure");
		}
	}

	void updateConnIDCallID(int newCallID) {
		if ((connID != null) && (newCallID == connID.getCallID())) {
			return;
		}

		if ((isTermConn) && (newCallID == 0)) {
			return;
		}

		CSTAConnectionID newID = new CSTAConnectionID(newCallID, connID
				.getDeviceID(), (short) connID.getDevIDType());

		setConnID(newID);
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
				"could not finish connection construction");
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSConnection JD-Core Version: 0.5.4
 */