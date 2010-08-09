package com.avaya.jtapi.tsapi.impl;

import javax.telephony.Call;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.CallCenterAddress;
import javax.telephony.callcenter.CallCenterTrunk;
import javax.telephony.privatedata.PrivateData;

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
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.impl.core.TsapiPromoter;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class TsapiRouteSession implements ITsapiRouteSession, ITsapiCallInfo,
		PrivateData {
	TSRouteSession tsRouteSession;
	CSTAPrivate privData = null;

	TsapiRouteSession(TSRouteSession _tsRouteSession) {
		tsRouteSession = _tsRouteSession;
		TsapiTrace.traceConstruction(this, TsapiRouteSession.class);
	}

	public final boolean canSetBillRate() {
		TsapiTrace.traceEntry("canSetBillRate[]", this);
		try {
			boolean can = tsRouteSession.canSetBillRate();
			TsapiTrace.traceExit("canSetBillRate[]", this);
			return can;
		} finally {
			privData = null;
		}
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
		TSProviderImpl provider = tsRouteSession.getTSProviderImpl();
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

	public final void endRoute(int errorValue) {
		TsapiTrace.traceEntry("endRoute[int errorValue]", this);
		try {
			tsRouteSession.tsEndRoute(errorValue, privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("endRoute[int errorValue]", this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TsapiRouteSession) {
			return tsRouteSession
					.equals(((TsapiRouteSession) obj).tsRouteSession);
		}

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiRouteSession.class);
	}

	public final Call getCall() {
		TsapiTrace.traceEntry("getCall[]", this);
		try {
			Call call = (Call) TsapiCreateObject.getTsapiObject(tsRouteSession
					.getCall(), false);
			TsapiTrace.traceExit("getCall[]", this);
			return call;
		} finally {
			privData = null;
		}
	}

	public final int getCallOriginatorType() {
		TsapiTrace.traceEntry("getCallOriginatorType[]", this);
		try {
			int type = tsRouteSession.getCallOriginatorType();
			TsapiTrace.traceExit("getCallOriginatorType[]", this);
			return type;
		} finally {
			privData = null;
		}
	}

	public final int getCause() {
		TsapiTrace.traceEntry("getCause[]", this);
		try {
			int i = tsRouteSession.tsGetCause();
			TsapiTrace.traceExit("getCause[]", this);
			return i;
		} finally {
			privData = null;
		}
	}

	public short getCSTAErrorCode() {
		TsapiTrace.traceEntry("getCSTAErrorCode[]", this);
		short code = tsRouteSession.getCSTAErrorCode();
		TsapiTrace.traceExit("getCSTAErrorCode[]", this);
		return code;
	}

	public final ACDAddress getDeliveringACDAddress() {
		TsapiTrace.traceEntry("getDeliveringACDAddress[]", this);
		try {
			TsapiTrace.traceExit("getDeliveringACDAddress[]", this);
			return null;
		} finally {
			privData = null;
		}
	}

	public final CallCenterAddress getDistributingAddress() {
		TsapiTrace.traceEntry("getDistributingAddress[]", this);
		try {
			TsapiTrace.traceExit("getDistributingAddress[]", this);
			return null;
		} finally {
			privData = null;
		}
	}

	public final CallCenterAddress getDistributingVDNAddress() {
		TsapiTrace.traceEntry("getDistributingVDNAddress[]", this);
		try {
			TsapiTrace.traceExit("getDistributingVDNAddress[]", this);
			return null;
		} finally {
			privData = null;
		}
	}

	public final LookaheadInfo getLookaheadInfo() {
		TsapiTrace.traceEntry("getLookaheadInfo[]", this);
		try {
			LookaheadInfo lai = tsRouteSession.getLAI();
			TsapiTrace.traceExit("getLookaheadInfo[]", this);
			return lai;
		} finally {
			privData = null;
		}
	}

	public final OriginalCallInfo getOriginalCallInfo() {
		TsapiTrace.traceEntry("getOriginalCallInfo[]", this);
		try {
			TsapiTrace.traceExit("getOriginalCallInfo[]", this);
			return null;
		} finally {
			privData = null;
		}
	}

	public final Object getPrivateData() {
		TsapiTrace.traceEntry("getPrivateData[]", this);
		TsapiTrace.traceExit("getPrivateData[]", this);
		return null;
	}

	public final short getReason() {
		TsapiTrace.traceEntry("getReason[]", this);
		try {
			TsapiTrace.traceExit("getReason[]", this);
			return 0;
		} finally {
			privData = null;
		}
	}

	// ERROR //
	public final javax.telephony.callcenter.RouteAddress getRouteAddress() {
		return null;
		// Byte code:
		// 0: ldc 1
		// 2: aload_0
		// 3: invokestatic 2 com/avaya/jtapi/tsapi/util/TsapiTrace:traceEntry
		// (Ljava/lang/String;Ljava/lang/Object;)V
		// 6: aload_0
		// 7: getfield 3
		// com/avaya/jtapi/tsapi/impl/TsapiRouteSession:tsRouteSession
		// Lcom/avaya/jtapi/tsapi/impl/core/TSRouteSession;
		// 10: invokevirtual 4
		// com/avaya/jtapi/tsapi/impl/core/TSRouteSession:getTSRouteDevice
		// ()Lcom/avaya/jtapi/tsapi/impl/core/TSDevice;
		// 13: astore_1
		// 14: aload_1
		// 15: ifnull +25 -> 40
		// 18: aload_1
		// 19: iconst_1
		// 20: invokestatic 5
		// com/avaya/jtapi/tsapi/impl/TsapiCreateObject:getTsapiObject
		// (Ljava/lang/Object;Z)Ljava/lang/Object;
		// 23: checkcast 6 javax/telephony/callcenter/RouteAddress
		// 26: astore_2
		// 27: ldc 1
		// 29: aload_0
		// 30: invokestatic 7 com/avaya/jtapi/tsapi/util/TsapiTrace:traceExit
		// (Ljava/lang/String;Ljava/lang/Object;)V
		// 33: aload_2
		// 34: astore_3
		// 35: jsr +25 -> 60
		// 38: aload_3
		// 39: areturn
		// 40: new 8 com/avaya/jtapi/tsapi/TsapiPlatformException
		// 43: dup
		// 44: iconst_4
		// 45: iconst_0
		// 46: ldc 9
		// 48: invokespecial 10
		// com/avaya/jtapi/tsapi/TsapiPlatformException:<init>
		// (IILjava/lang/String;)V
		// 51: athrow
		// 52: astore 4
		// 54: jsr +6 -> 60
		// 57: aload 4
		// 59: athrow
		// 60: astore 5
		// 62: aload_0
		// 63: aconst_null
		// 64: putfield 11 com/avaya/jtapi/tsapi/impl/TsapiRouteSession:privData
		// Lcom/avaya/jtapi/tsapi/csta1/CSTAPrivate;
		// 67: ret 5
		//
		// Exception table:
		// from to target type
		// 6 38 52 finally
		// 40 57 52 finally
	}

	public final int getRouteCrossRefID() {
		TsapiTrace.traceEntry("getRouteCrossRefID[]", this);
		try {
			int xref = tsRouteSession.getRtXrefID();
			TsapiTrace.traceExit("getRouteCrossRefID[]", this);
			return xref;
		} finally {
			privData = null;
		}
	}

	public final int getRouteRegisterID() {
		TsapiTrace.traceEntry("getRouteRegisterID[]", this);
		try {
			int id = tsRouteSession.getRtRegID();
			TsapiTrace.traceExit("getRouteRegisterID[]", this);
			return id;
		} finally {
			privData = null;
		}
	}

	public final int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		try {
			int state = tsRouteSession.tsGetState();
			TsapiTrace.traceExit("getState[]", this);
			return state;
		} finally {
			privData = null;
		}
	}

	public final CallCenterTrunk getTrunk() {
		TsapiTrace.traceEntry("getTrunk[]", this);
		try {
			TSTrunk tsTrunk = tsRouteSession.getTrunk();
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
			privData = null;
		}
	}

	public final String getUCID() {
		TsapiTrace.traceEntry("getUCID[]", this);
		try {
			String ucid = tsRouteSession.getUCID();
			TsapiTrace.traceExit("getUCID[]", this);
			return ucid;
		} finally {
			privData = null;
		}
	}

	public final UserEnteredCode getUserEnteredCode() {
		TsapiTrace.traceEntry("getUserEnteredCode[]", this);
		try {
			UserEnteredCode uec = tsRouteSession.getUEC();
			TsapiTrace.traceExit("getUserEnteredCode[]", this);
			return uec;
		} finally {
			privData = null;
		}
	}

	public final UserToUserInfo getUserToUserInfo() {
		TsapiTrace.traceEntry("getUserToUserInfo[]", this);
		try {
			UserToUserInfo uui = tsRouteSession.getUUI();
			TsapiTrace.traceExit("getUserToUserInfo[]", this);
			return uui;
		} finally {
			privData = null;
		}
	}

	public final boolean hasCallOriginatorType() {
		TsapiTrace.traceEntry("hasCallOriginatorType[]", this);
		try {
			boolean has = tsRouteSession.hasCallOriginatorType();
			TsapiTrace.traceExit("hasCallOriginatorType[]", this);
			return has;
		} finally {
			privData = null;
		}
	}

	@Override
	public final int hashCode() {
		return tsRouteSession.hashCode();
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
			privData = lrs.makeTsapiPrivate();
			tsRouteSession.tsSelectRoute(routes, privData);
		} finally {
			privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRoute[String routeSelected, boolean priorityCall, UserToUserInfo userInfo]",
						this);
	}

	public final void selectRoute(String[] routeSelected)
			throws TsapiMethodNotSupportedException {
		TsapiTrace.traceEntry("selectRoute[String[] routeSelected]", this);
		try {
			tsRouteSession.tsSelectRoute(routeSelected, privData);
		} finally {
			privData = null;
		}
		TsapiTrace.traceExit("selectRoute[String[] routeSelected]", this);
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

			privData = lrs.makeTsapiPrivate();
			String[] routes = new String[1];
			routes[0] = routeSelected;
			tsRouteSession.tsSelectRoute(routes, privData);
		} finally {
			privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteAndCollect[String routeSelected, int digitsToBeCollected, int timeout, boolean priorityCall, UserToUserInfo userInfo]",
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

			privData = lrs.makeTsapiPrivate();
			String[] routes = new String[1];
			routes[0] = calledAgent.getAgentAddress().getName();
			tsRouteSession.tsSelectRoute(routes, privData);
		} finally {
			privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteDirectAgent[LucentAgent calledAgent, boolean priorityCall, UserToUserInfo userInfo]",
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
			if (digits == null) {
				upc = null;
			} else {
				upc = new LucentUserProvidedCode((short) 17, digits);
			}
			LucentRouteSelect lrs = createLucentRouteSelect(null, null,
					priorityCall, null, null, upc, userInfo, (short) 0);

			privData = lrs.makeTsapiPrivate();
			String[] routes = new String[1];
			routes[0] = routeSelected;
			tsRouteSession.tsSelectRoute(routes, privData);
		} finally {
			privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteWithDigits[String routeSelected, String digits, boolean priorityCall, UserToUserInfo userInfo]",
						this);
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
			privData = lrs.makeTsapiPrivate();
			tsRouteSession.tsSelectRoute(routes, privData);
		} finally {
			privData = null;
		}
		TsapiTrace
				.traceExit(
						"selectRouteWithNetworkRedirection[String routeSelected, UserToUserInfo userInfo]",
						this);
	}

	public final Object sendPrivateData(Object data) {
		TsapiTrace.traceEntry("sendPrivateData[Object data]", this);
		TsapiTrace.traceExit("sendPrivateData[Object data]", this);
		return null;
	}

	public final void setPrivateData(Object data) {
		TsapiTrace.traceEntry("setPrivateData[Object data]", this);
		try {
			privData = TsapiPromoter.demoteTsapiPrivate((TsapiPrivate) data);
		} catch (ClassCastException e) {
			throw new TsapiPlatformException(3, 0,
					"data is not a TsapiPrivate object");
		}

		TsapiTrace.traceExit("setPrivateData[Object data]", this);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiRouteSession JD-Core Version: 0.5.4
 */