package com.avaya.jtapi.tsapi.impl;

import javax.telephony.Call;
import javax.telephony.Connection;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public class TsapiTrunkImpl extends TsapiTrunk {
	public static String makeTrunkName(final String groupName,
			final String memberName) {
		TsapiTrace.traceEntry(
				"makeTrunkName[String groupName, String memberName]", null);
		String name = null;
		if (groupName == null)
			name = null;
		else if (memberName == null)
			name = groupName + ":0";
		else
			name = groupName + ":" + memberName;
		TsapiTrace.traceExit(
				"makeTrunkName[String groupName, String memberName]", null);
		return name;
	}

	TSTrunk tsTrunk;

	String name;

	TsapiTrunkImpl(final TSProviderImpl tsProvider, final String trkName) {
		tsTrunk = tsProvider.createTSTrunk(trkName);
		if (tsTrunk == null)
			throw new TsapiPlatformException(4, 0, "could not create trunk");
		name = trkName;
		TsapiTrace.traceConstruction(this, TsapiTrunkImpl.class);
	}

	TsapiTrunkImpl(final TSTrunk _tsTrunk) {
		tsTrunk = _tsTrunk;

		name = tsTrunk.getName();
		TsapiTrace.traceConstruction(this, TsapiTrunkImpl.class);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TsapiTrunkImpl)
			return tsTrunk.equals(((TsapiTrunkImpl) obj).tsTrunk);

		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiTrunkImpl.class);
	}

	@Override
	public Call getCall() {
		TsapiTrace.traceEntry("getCall[]", this);
		final TSCall tsCall = tsTrunk.getTSCall();
		Call call = null;
		if (tsCall != null)
			call = (Call) TsapiCreateObject.getTsapiObject(tsCall, false);
		TsapiTrace.traceExit("getCall[]", this);
		return call;
	}

	@Override
	public Connection getConnection() {
		TsapiTrace.traceEntry("getConnection[]", this);
		final TSConnection tsconn = tsTrunk.getTSConnection();
		Connection conn = null;
		if (tsconn != null)
			conn = (Connection) TsapiCreateObject.getTsapiObject(tsconn, true);
		TsapiTrace.traceEntry("getConnection[]", this);
		return conn;
	}

	@Override
	public String getGroupName() {
		TsapiTrace.traceEntry("getGroupName[]", this);
		final String name = tsTrunk.getGroupName();
		TsapiTrace.traceExit("getGroupName[]", this);
		return name;
	}

	@Override
	public String getMemberName() {
		TsapiTrace.traceEntry("getMemberName[]", this);
		final String name = tsTrunk.getMemberName();
		TsapiTrace.traceExit("getMemberName[]", this);
		return name;
	}

	@Override
	public String getName() {
		TsapiTrace.traceEntry("getName[]", this);
		TsapiTrace.traceExit("getName[]", this);
		return name;
	}

	@Override
	public int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		TsapiTrace.traceExit("getState[]", this);
		return tsTrunk.getState();
	}

	public TSTrunk getTSTrunk() {
		TsapiTrace.traceEntry("getTSTrunk[]", this);
		TsapiTrace.traceExit("getTSTrunk[]", this);
		return tsTrunk;
	}

	@Override
	public int getType() {
		TsapiTrace.traceEntry("getType[]", this);
		final int type = tsTrunk.getType();
		TsapiTrace.traceExit("getType[]", this);
		return type;
	}

	@Override
	public int hashCode() {
		return tsTrunk.hashCode();
	}
}
