package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
import com.avaya.jtapi.tsapi.csta1.CSTACallOriginatorInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.LucentLookaheadInfo;
import com.avaya.jtapi.tsapi.csta1.LucentUserEnteredCode;
import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
import org.apache.log4j.Logger;

public final class TSRouteSession {
	private static Logger log = Logger.getLogger(TSRouteSession.class);

	UserToUserInfo uui = null;
	LookaheadInfo lai = null;
	UserEnteredCode uec = null;

	String ucid = null;
	CSTACallOriginatorInfo callOriginatorInfo;
	boolean flexibleBilling;
	V7DeviceHistoryEntry[] deviceHistory = null;

	TSTrunk trunk = null;
	TSProviderImpl tsProvider;
	TSDevice tsDevice;
	int routingCrossRefID;
	public TSDevice currentRouteDevice;
	TSCall call;
	public TSDevice callingAddress;
	public TSDevice callingTerminal;
	public int routeSelectAlgorithm;
	public String isdnSetupMessage;
	public TSDevice routeUsedDevice;
	public boolean outOfDomain;
	int state;
	int cause;
	short csta_error;

	void dump(String indent) {
		log.trace(indent + "***** TSRouteSession DUMP *****");
		log.trace(indent + "TSRouteSession: " + this);
		log.trace(indent + "***** TSRouteSession DUMP END *****");
	}

	public TSProviderImpl getTSProviderImpl() {
		return this.tsProvider;
	}

	public TSDevice getTSRouteDevice() {
		return this.tsDevice;
	}

	public synchronized void tsSelectRoute(String[] routeSelected,
			CSTAPrivate reqPriv) throws TsapiMethodNotSupportedException {
		if (this.tsProvider.getCapabilities().getRouteSelect() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		if (this.state == 3) {
			int cstaError = 17;

			String errorString = "Route already ended.";
			throw new TsapiPlatformException(2, cstaError, errorString);
		}

		for (int i = 0; i < routeSelected.length; i++) {
			this.tsProvider.tsapi.selectRoute(this.tsDevice.getRegisterReqID(),
					this.routingCrossRefID, routeSelected[i], -2, "", true,
					reqPriv);
			try {
				wait(TSProviderImpl.TSAPI_RESPONSE_TIME);
			} catch (InterruptedException e) {
				return;
			}
			if (this.state != 4) {
				return;
			}
		}
	}

	public void tsEndRoute(int errorValue, CSTAPrivate reqPriv) {
		int cstaError;
		switch (errorValue) {
		case 2:
			cstaError = 33;
			break;
		case 3:
			cstaError = 34;
			break;
		case 1:
		default:
			cstaError = 0;
		}

		this.tsProvider.tsapi.routeEnd(this.tsDevice.getRegisterReqID(),
				this.routingCrossRefID, (short) cstaError, reqPriv);

		TSEvent routeEndEvent = setState(3);
		this.tsDevice.getTSRouteCallback().deliverEvent(routeEndEvent);
	}

	public synchronized int tsGetState() {
		return this.state;
	}

	public int tsGetCause() {
		return this.cause;
	}

	public UserToUserInfo getUUI() {
		return this.uui;
	}

	public LookaheadInfo getLAI() {
		return this.lai;
	}

	public UserEnteredCode getUEC() {
		return this.uec;
	}

	public String getUCID() {
		return this.ucid;
	}

	public TSCall getCall() {
		return this.call;
	}

	public int getCallOriginatorType() {
		if (hasCallOriginatorType()) {
			return this.callOriginatorInfo.getCallOriginatorType();
		}
		return -1;
	}

	public boolean hasCallOriginatorType() {
		return this.callOriginatorInfo != null;
	}

	public boolean canSetBillRate() {
		return this.flexibleBilling;
	}

	public TSTrunk getTrunk() {
		return this.trunk;
	}

	public synchronized TSEvent setState(int _state) {
		this.state = _state;
		switch (_state) {
		case 1:
			return new TSEvent(63, this);
		case 4:
			notify();
			return new TSEvent(60, this);
		case 2:
			notify();
			return new TSEvent(64, this);
		case 3:
			notify();
			this.tsDevice.deleteSession(this.routingCrossRefID);
			return new TSEvent(62, this);
		}
		return null;
	}

	public int getRtRegID() {
		return this.tsDevice.getRegisterReqID();
	}

	public int getRtXrefID() {
		return this.routingCrossRefID;
	}

	TSRouteSession(TSProviderImpl _TSProviderImpl, TSDevice _tsDevice,
			int _routingCrossRefID, TSDevice _currentRouteDevice,
			TSDevice _callingDevice, TSCall _call, int _routeSelectAlgorithm,
			String _isdnSetupMessage) {
		this.tsProvider = _TSProviderImpl;
		this.tsDevice = _tsDevice;
		this.routingCrossRefID = _routingCrossRefID;
		this.currentRouteDevice = _currentRouteDevice;
		if (_callingDevice.isTerminal())
			this.callingTerminal = _callingDevice;
		this.callingAddress = _callingDevice;
		this.call = _call;
		switch (_routeSelectAlgorithm) {
		case 0:
			this.routeSelectAlgorithm = 0;
			break;
		case 1:
			this.routeSelectAlgorithm = 1;
			break;
		case 2:
			this.routeSelectAlgorithm = 2;
			break;
		case 3:
			this.routeSelectAlgorithm = 3;
			break;
		case 4:
			this.routeSelectAlgorithm = 4;
			break;
		default:
			this.routeSelectAlgorithm = 0;
		}

		this.isdnSetupMessage = _isdnSetupMessage;
		this.state = 1;
		this.cause = 100;
		this.tsDevice.addSession(this.routingCrossRefID, this);
		log.info("Constructing route session " + this + " with xrefID "
				+ this.routingCrossRefID + " for " + this.tsProvider);
	}

	void setCallingDevice(TSDevice _callingDevice) {
		if (_callingDevice.isTerminal())
			this.callingTerminal = _callingDevice;
		this.callingAddress = _callingDevice;
	}

	void setRouteUsedDevice(TSDevice _routeUsedDevice) {
		this.routeUsedDevice = _routeUsedDevice;
	}

	void setDomain(boolean _outOfDomain) {
		this.outOfDomain = _outOfDomain;
	}

	void setCause(int error) {
		this.csta_error = (short) error;
		switch (error) {
		case 0:
			this.cause = 100;
			break;
		case 14:
			this.cause = 103;
			break;
		case 24:
		case 28:
			this.cause = 104;
			break;
		case 5:
		case 8:
		case 18:
		case 22:
		case 33:
		case 52:
		default:
			this.cause = 105;
		}
	}

	void setUUI(LucentUserToUserInfo _uui) {
		if (_uui != null) {
			this.uui = TsapiPromoter.promoteUserToUserInfo(_uui);
		}
	}

	void setLAI(LucentLookaheadInfo _lai) {
		if (_lai != null) {
			this.lai = TsapiPromoter.promoteLookaheadInfo(_lai);
		}
	}

	void setUEC(LucentUserEnteredCode _uec) {
		if (_uec != null) {
			this.uec = TsapiPromoter.promoteUserEnteredCode(this.tsProvider,
					_uec);
		}
	}

	void setUUI(UserToUserInfo _uui) {
		if (_uui != null) {
			this.uui = _uui;
		}
	}

	void setLAI(LookaheadInfo _lai) {
		if (_lai != null) {
			this.lai = _lai;
		}
	}

	void setUEC(UserEnteredCode _uec) {
		if (_uec != null) {
			this.uec = _uec;
		}
	}

	void setUCID(String _ucid) {
		if (_ucid != null) {
			this.ucid = _ucid;
		}
	}

	void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo) {
		this.callOriginatorInfo = _callOriginatorInfo;
	}

	void setFlexibleBilling(boolean _flexibleBilling) {
		this.flexibleBilling = _flexibleBilling;
	}

	void setTrunk(TSTrunk _trunk) {
		if (_trunk != null) {
			this.trunk = _trunk;
		}
	}

	public short getCSTAErrorCode() {
		return this.csta_error;
	}

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		return this.deviceHistory;
	}

	void setDeviceHistory(V7DeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}
}