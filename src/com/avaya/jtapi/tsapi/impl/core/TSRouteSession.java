package com.avaya.jtapi.tsapi.impl.core;

import org.apache.log4j.Logger;

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

	TSRouteSession(TSProviderImpl _TSProviderImpl, TSDevice _tsDevice,
			int _routingCrossRefID, TSDevice _currentRouteDevice,
			TSDevice _callingDevice, TSCall _call, int _routeSelectAlgorithm,
			String _isdnSetupMessage) {
		tsProvider = _TSProviderImpl;
		tsDevice = _tsDevice;
		routingCrossRefID = _routingCrossRefID;
		currentRouteDevice = _currentRouteDevice;
		if (_callingDevice.isTerminal()) {
			callingTerminal = _callingDevice;
		}
		callingAddress = _callingDevice;
		call = _call;
		switch (_routeSelectAlgorithm) {
		case 0:
			routeSelectAlgorithm = 0;
			break;
		case 1:
			routeSelectAlgorithm = 1;
			break;
		case 2:
			routeSelectAlgorithm = 2;
			break;
		case 3:
			routeSelectAlgorithm = 3;
			break;
		case 4:
			routeSelectAlgorithm = 4;
			break;
		default:
			routeSelectAlgorithm = 0;
		}

		isdnSetupMessage = _isdnSetupMessage;
		state = 1;
		cause = 100;
		tsDevice.addSession(routingCrossRefID, this);
		log.info("Constructing route session " + this + " with xrefID "
				+ routingCrossRefID + " for " + tsProvider);
	}

	public boolean canSetBillRate() {
		return flexibleBilling;
	}

	void dump(String indent) {
		log.trace(indent + "***** TSRouteSession DUMP *****");
		log.trace(indent + "TSRouteSession: " + this);
		log.trace(indent + "***** TSRouteSession DUMP END *****");
	}

	public TSCall getCall() {
		return call;
	}

	public int getCallOriginatorType() {
		if (hasCallOriginatorType()) {
			return callOriginatorInfo.getCallOriginatorType();
		}
		return -1;
	}

	public short getCSTAErrorCode() {
		return csta_error;
	}

	public V7DeviceHistoryEntry[] getDeviceHistory() {
		return deviceHistory;
	}

	public LookaheadInfo getLAI() {
		return lai;
	}

	public int getRtRegID() {
		return tsDevice.getRegisterReqID();
	}

	public int getRtXrefID() {
		return routingCrossRefID;
	}

	public TSTrunk getTrunk() {
		return trunk;
	}

	public TSProviderImpl getTSProviderImpl() {
		return tsProvider;
	}

	public TSDevice getTSRouteDevice() {
		return tsDevice;
	}

	public String getUCID() {
		return ucid;
	}

	public UserEnteredCode getUEC() {
		return uec;
	}

	public UserToUserInfo getUUI() {
		return uui;
	}

	public boolean hasCallOriginatorType() {
		return callOriginatorInfo != null;
	}

	void setCallingDevice(TSDevice _callingDevice) {
		if (_callingDevice.isTerminal()) {
			callingTerminal = _callingDevice;
		}
		callingAddress = _callingDevice;
	}

	void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo) {
		callOriginatorInfo = _callOriginatorInfo;
	}

	void setCause(int error) {
		csta_error = (short) error;
		switch (error) {
		case 0:
			cause = 100;
			break;
		case 14:
			cause = 103;
			break;
		case 24:
		case 28:
			cause = 104;
			break;
		case 5:
		case 8:
		case 18:
		case 22:
		case 33:
		case 52:
		default:
			cause = 105;
		}
	}

	void setDeviceHistory(V7DeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}

	void setDomain(boolean _outOfDomain) {
		outOfDomain = _outOfDomain;
	}

	void setFlexibleBilling(boolean _flexibleBilling) {
		flexibleBilling = _flexibleBilling;
	}

	void setLAI(LookaheadInfo _lai) {
		if (_lai == null) {
			return;
		}
		lai = _lai;
	}

	void setLAI(LucentLookaheadInfo _lai) {
		if (_lai == null) {
			return;
		}
		lai = TsapiPromoter.promoteLookaheadInfo(_lai);
	}

	void setRouteUsedDevice(TSDevice _routeUsedDevice) {
		routeUsedDevice = _routeUsedDevice;
	}

	public synchronized TSEvent setState(int _state) {
		state = _state;
		switch (_state) {
		case 1:
			return new TSEvent(62, this);
		case 4:
			super.notify();
			return new TSEvent(59, this);
		case 2:
			super.notify();
			return new TSEvent(63, this);
		case 3:
			super.notify();
			tsDevice.deleteSession(routingCrossRefID);
			return new TSEvent(61, this);
		}
		return null;
	}

	void setTrunk(TSTrunk _trunk) {
		if (_trunk == null) {
			return;
		}
		trunk = _trunk;
	}

	void setUCID(String _ucid) {
		if (_ucid == null) {
			return;
		}
		ucid = _ucid;
	}

	void setUEC(LucentUserEnteredCode _uec) {
		if (_uec == null) {
			return;
		}
		uec = TsapiPromoter.promoteUserEnteredCode(tsProvider, _uec);
	}

	void setUEC(UserEnteredCode _uec) {
		if (_uec == null) {
			return;
		}
		uec = _uec;
	}

	void setUUI(LucentUserToUserInfo _uui) {
		if (_uui == null) {
			return;
		}
		uui = TsapiPromoter.promoteUserToUserInfo(_uui);
	}

	void setUUI(UserToUserInfo _uui) {
		if (_uui == null) {
			return;
		}
		uui = _uui;
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

		tsProvider.tsapi.routeEnd(tsDevice.getRegisterReqID(),
				routingCrossRefID, (short) cstaError, reqPriv);

		TSEvent routeEndEvent = setState(3);
		tsDevice.getTSRouteCallback().deliverEvent(routeEndEvent);
	}

	public int tsGetCause() {
		return cause;
	}

	public int tsGetState() {
		return state;
	}

	public synchronized void tsSelectRoute(String[] routeSelected,
			CSTAPrivate reqPriv) throws TsapiMethodNotSupportedException {
		if (tsProvider.getCapabilities().getRouteSelect() == 0) {
			throw new TsapiMethodNotSupportedException(4, 0,
					"unsupported by driver");
		}

		if (state == 3) {
			int cstaError = 17;

			String errorString = "Route already ended.";
			throw new TsapiPlatformException(2, cstaError, errorString);
		}

		for (int i = 0; i < routeSelected.length; ++i) {
			tsProvider.tsapi.selectRoute(tsDevice.getRegisterReqID(),
					routingCrossRefID, routeSelected[i], -2, "", true, reqPriv);
			try {
				super.wait(TSProviderImpl.TSAPI_RESPONSE_TIME);
			} catch (InterruptedException e) {
				return;
			}
			if (state != 4) {
				return;
			}
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TSRouteSession JD-Core Version: 0.5.4
 */