package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.ITsapiCallInfo;
import com.avaya.jtapi.tsapi.ITsapiRouteSession;
import com.avaya.jtapi.tsapi.LookaheadInfo;
import com.avaya.jtapi.tsapi.LucentAgent;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.UserEnteredCode;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.LucentRouteSelect;
import com.avaya.jtapi.tsapi.csta1.LucentUserCollectCode;
import com.avaya.jtapi.tsapi.csta1.LucentUserProvidedCode;
import com.avaya.jtapi.tsapi.csta1.LucentUserToUserInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV6RouteSelect;
import com.avaya.jtapi.tsapi.csta1.LucentV7RouteSelect;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.callcenter.RouteAddress;
import javax.telephony.privatedata.PrivateData;

@SuppressWarnings("unused")
public class TsapiRouteSession implements ITsapiRouteSession, ITsapiCallInfo,
		PrivateData {
	TSRouteSession tsRouteSession;
	CSTAPrivate privData = null;

	public final RouteAddress getRouteAddress() {
		TsapiTrace.traceEntry("getRouteAddress[]", this);
		try {
			TSDevice tsRouteDevice = this.tsRouteSession.getTSRouteDevice();
			if (tsRouteDevice != null) {
				RouteAddress addr = (RouteAddress) TsapiCreateObject
						.getTsapiObject(tsRouteDevice, true);
				TsapiTrace.traceExit("getRouteAddress[]", this);
				return addr;
			}

			throw new TsapiPlatformException(4, 0, "could not locate address");
		} finally {
			this.privData = null;
		}
	}

	public final void selectRoute(String[] routeSelected)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("selectRoute[String[] routeSelected]", this);
		try {
			this.tsRouteSession.tsSelectRoute(routeSelected, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("selectRoute[String[] routeSelected]", this);
	}

	private LucentRouteSelect createLucentRouteSelect(String callingDevice,
			String directAgentCallSplit, boolean priorityCalling,
			String destRoute, LucentUserCollectCode collectCode,
			LucentUserProvidedCode userProvidedCode, UserToUserInfo userInfo,
			short networkRedirectCallType) {
		TsapiTrace
				.traceEntry(
						"createLucentRouteSelect[String callingDevice, String directAgentCallSplit, boolean priorityCalling, String destRoute, UserCollectCode collectCode, UserProvidedCode userProvidedCode, UserToUserInfo userInfo, short networkRedirectCallType]",
						this);
		TSProviderImpl provider = this.tsRouteSession.getTSProviderImpl();
		LucentUserToUserInfo asn_uui = TsapiPromoter
				.demoteUserToUserInfo(userInfo);

		LucentRouteSelect route = null;
		if (provider != null) {
			if (provider.isLucentV7()) {
				route = new LucentV7RouteSelect(callingDevice,
						directAgentCallSplit, priorityCalling, destRoute,
						collectCode, userProvidedCode, asn_uui,
						networkRedirectCallType);
			} else if (provider.isLucentV6()) {
				route = new LucentV6RouteSelect(callingDevice,
						directAgentCallSplit, priorityCalling, destRoute,
						collectCode, userProvidedCode, asn_uui);
			} else {
				route = new LucentRouteSelect(callingDevice,
						directAgentCallSplit, priorityCalling, destRoute,
						collectCode, userProvidedCode, asn_uui);
			}

		} else {
			route = null;
		}
		TsapiTrace
				.traceExit(
						"createLucentRouteSelect[String callingDevice, String directAgentCallSplit, boolean priorityCalling, String destRoute, UserCollectCode collectCode, UserProvidedCode userProvidedCode, UserToUserInfo userInfo, short networkRedirectCallType]",
						this);
		return route;
	}

	public final void selectRoute(String routeSelected, boolean priorityCall,
			UserToUserInfo userInfo) throws TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"selectRoute[String routeSelected, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		String[] routes = new String[1];
		routes[0] = routeSelected;
		try {
			LucentRouteSelect lrs = createLucentRouteSelect(null, null,
					priorityCall, null, null, null, userInfo, (short) 0);
			this.privData = lrs.makeTsapiPrivate();
			this.tsRouteSession.tsSelectRoute(routes, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRoute[String routeSelected, boolean priorityCall, UserToUserInfo userInfo]",
						this);
	}

	public final void selectRouteDirectAgent(LucentAgent calledAgent,
			boolean priorityCall, UserToUserInfo userInfo)
			throws TsapiMethodNotSupportedException,
			TsapiInvalidArgumentException {
		TsapiTrace
				.traceEntry(
						"selectRouteDirectAgent[LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		try {
			if (calledAgent == null) {
				throw new TsapiInvalidArgumentException(3, 0,
						"called Agent is null");
			}

			LucentRouteSelect lrs = createLucentRouteSelect(null, calledAgent
					.getACDAddress().getName(), priorityCall, null, null, null,
					userInfo, (short) 0);

			this.privData = lrs.makeTsapiPrivate();
			String[] routes = new String[1];
			routes[0] = calledAgent.getAgentAddress().getName();
			this.tsRouteSession.tsSelectRoute(routes, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteDirectAgent[LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
						this);
	}

	public final void selectRouteAndCollect(String routeSelected,
			int digitsToBeCollected, int timeout, boolean priorityCall,
			UserToUserInfo userInfo) throws TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"selectRouteAndCollect[String routeSelected, int digitsToBeCollected, int timeout, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		try {
			LucentUserCollectCode ucc = new LucentUserCollectCode((short) 32,
					digitsToBeCollected, timeout, null, (short) 11);

			LucentRouteSelect lrs = createLucentRouteSelect(null, null,
					priorityCall, null, ucc, null, userInfo, (short) 0);

			this.privData = lrs.makeTsapiPrivate();
			String[] routes = new String[1];
			routes[0] = routeSelected;
			this.tsRouteSession.tsSelectRoute(routes, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteAndCollect[String routeSelected, int digitsToBeCollected, int timeout, boolean priorityCall, UserToUserInfo userInfo]",
						this);
	}

	public final void selectRouteWithDigits(String routeSelected,
			String digits, boolean priorityCall, UserToUserInfo userInfo)
			throws TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"selectRouteWithDigits[String routeSelected, String digits, boolean priorityCall, UserToUserInfo userInfo]",
						this);
		try {
			LucentUserProvidedCode upc;
			if (digits == null)
				upc = null;
			else {
				upc = new LucentUserProvidedCode((short) 17, digits);
			}
			LucentRouteSelect lrs = createLucentRouteSelect(null, null,
					priorityCall, null, null, upc, userInfo, (short) 0);

			this.privData = lrs.makeTsapiPrivate();
			String[] routes = new String[1];
			routes[0] = routeSelected;
			this.tsRouteSession.tsSelectRoute(routes, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteWithDigits[String routeSelected, String digits, boolean priorityCall, UserToUserInfo userInfo]",
						this);
	}

	public final void endRoute(int errorValue) {
		TsapiTrace.traceEntry("endRoute[int errorValue]", this);
		try {
			this.tsRouteSession.tsEndRoute(errorValue, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace.traceExit("endRoute[int errorValue]", this);
	}

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			int state = this.tsRouteSession.tsGetState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			this.privData = null;
		}
	}

	public final int getCause() {
		TsapiTrace.traceEntry("getCause[]", this);
		try {
			int i = this.tsRouteSession.tsGetCause();
			TsapiTrace.traceExit("getCause[]", this);
			return i;
		} finally {
			this.privData = null;
		}
	}

	public final UserToUserInfo getUserToUserInfo() {
		TsapiTrace.traceEntry("getUserToUserInfo[]", this);
		try {
			UserToUserInfo uui = this.tsRouteSession.getUUI();
			TsapiTrace.traceExit("getUserToUserInfo[]", this);
			return uui;
		} finally {
			this.privData = null;
		}
	}

	public final LookaheadInfo getLookaheadInfo() {
		TsapiTrace.traceEntry("getLookaheadInfo[]", this);
		try {
			LookaheadInfo lai = this.tsRouteSession.getLAI();
			TsapiTrace.traceExit("getLookaheadInfo[]", this);
			return lai;
		} finally {
			this.privData = null;
		}
	}

	public final UserEnteredCode getUserEnteredCode() {
		TsapiTrace.traceEntry("getUserEnteredCode[]", this);
		try {
			UserEnteredCode uec = this.tsRouteSession.getUEC();
			TsapiTrace.traceExit("getUserEnteredCode[]", this);
			return uec;
		} finally {
			this.privData = null;
		}
	}

	public final CallCenterTrunk getTrunk() {
		TsapiTrace.traceEntry("getTrunk[]", this);
		try {
			TSTrunk tsTrunk = this.tsRouteSession.getTrunk();
			CallCenterTrunk trunk;
			if (tsTrunk != null) {
				trunk = (TsapiTrunk) TsapiCreateObject.getTsapiObject(tsTrunk,
						false);
				TsapiTrace.traceExit("getTrunk[]", this);
				return trunk;
			}

			TsapiTrace.traceExit("getTrunk[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final OriginalCallInfo getOriginalCallInfo() {
		TsapiTrace.traceEntry("getOriginalCallInfo[]", this);
		try {
			TsapiTrace.traceExit("getOriginalCallInfo[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final CallCenterAddress getDistributingAddress() {
		TsapiTrace.traceEntry("getDistributingAddress[]", this);
		try {
			TsapiTrace.traceExit("getDistributingAddress[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final CallCenterAddress getDistributingVDNAddress() {
		TsapiTrace.traceEntry("getDistributingVDNAddress[]", this);
		try {
			TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final ACDAddress getDeliveringACDAddress() {
		TsapiTrace.traceEntry("getDeliveringACDAddress[]", this);
		try {
			TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
			return null;
		} finally {
			this.privData = null;
		}
	}

	public final short getReason() {
		TsapiTrace.traceEntry("getReason[]", this);
		try {
			TsapiTrace.traceExit("getReason[]", this);
			return 0;
		} finally {
			this.privData = null;
		}
	}

	public final String getUCID() {
		TsapiTrace.traceEntry("getUCID[]", this);
		try {
			String ucid = this.tsRouteSession.getUCID();
			TsapiTrace.traceExit("getUCID[]", this);
			return ucid;
		} finally {
			this.privData = null;
		}
	}

	public final int getCallOriginatorType() {
		TsapiTrace.traceEntry("getCallOriginatorType[]", this);
		try {
			int type = this.tsRouteSession.getCallOriginatorType();
			TsapiTrace.traceExit("getCallOriginatorType[]", this);
			return type;
		} finally {
			this.privData = null;
		}
	}

	public final Call getCall() {
		TsapiTrace.traceEntry("getCall[]", this);
		try {
			Call call = (Call) TsapiCreateObject.getTsapiObject(
					this.tsRouteSession.getCall(), false);
			TsapiTrace.traceExit("getCall[]", this);
			return call;
		} finally {
			this.privData = null;
		}
	}

	public final boolean hasCallOriginatorType() {
		TsapiTrace.traceEntry("hasCallOriginatorType[]", this);
		try {
			boolean has = this.tsRouteSession.hasCallOriginatorType();
			TsapiTrace.traceExit("hasCallOriginatorType[]", this);
			return has;
		} finally {
			this.privData = null;
		}
	}

	public final boolean canSetBillRate() {
		TsapiTrace.traceEntry("canSetBillRate[]", this);
		try {
			boolean can = this.tsRouteSession.canSetBillRate();
			TsapiTrace.traceExit("canSetBillRate[]", this);
			return can;
		} finally {
			this.privData = null;
		}
	}

	public void selectRouteWithNetworkRedirection(String routeSelected,
			UserToUserInfo userInfo) throws TsapiMethodNotSupportedException {
		TsapiTrace
				.traceEntry(
						"selectRouteWithNetworkRedirection[String routeSelected, UserToUserInfo userInfo]",
						this);
		String[] routes = new String[1];
		routes[0] = routeSelected;
		try {
			LucentRouteSelect lrs = createLucentRouteSelect(null, null, false,
					null, null, null, userInfo, (short) 1);
			this.privData = lrs.makeTsapiPrivate();
			this.tsRouteSession.tsSelectRoute(routes, this.privData);
		} finally {
			this.privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteWithNetworkRedirection[String routeSelected, UserToUserInfo userInfo]",
						this);
	}

	public short getCSTAErrorCode() {
		TsapiTrace.traceEntry("getCSTAErrorCode[]", this);
		short code = this.tsRouteSession.getCSTAErrorCode();
		TsapiTrace.traceExit("getCSTAErrorCode[]", this);
		return code;
	}

	public final void setPrivateData(Object data) {
		TsapiTrace.traceEntry("setPrivateData[Object data]", this);
		try {
			this.privData = TsapiPromoter
					.demoteTsapiPrivate((TsapiPrivate) data);
		} catch (ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}

		TsapiTrace.traceExit("setPrivateData[Object data]", this);
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		TsapiTrace.traceExit("getPrivateData[]", this);
		return null;
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		TsapiTrace.traceExit("sendPrivateData[Object data]", this);
		return null;
	}

	public final int getRouteRegisterID() {
		TsapiTrace.traceEntry("getRouteRegisterID[]", this);
		try {
			int id = this.tsRouteSession.getRtRegID();
			TsapiTrace.traceExit("getRouteRegisterID[]", this);
			return id;
		} finally {
			this.privData = null;
		}
	}

	public final int getRouteCrossRefID() {
		TsapiTrace.traceEntry("getRouteCrossRefID[]", this);
		try {
			int xref = this.tsRouteSession.getRtXrefID();
			TsapiTrace.traceExit("getRouteCrossRefID[]", this);
			return xref;
		} finally {
			this.privData = null;
		}
	}

	public final int hashCode() {
		return this.tsRouteSession.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiRouteSession)) {
			return this.tsRouteSession
					.equals(((TsapiRouteSession) obj).tsRouteSession);
		}

		return false;
	}

	TsapiRouteSession(TSRouteSession _tsRouteSession) {
		this.tsRouteSession = _tsRouteSession;
		TsapiTrace.traceConstruction(this, TsapiRouteSession.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiRouteSession.class);
	}
}