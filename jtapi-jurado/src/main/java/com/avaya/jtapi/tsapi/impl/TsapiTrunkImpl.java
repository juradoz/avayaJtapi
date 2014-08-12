package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import com.avaya.jtapi.tsapi.TsapiTrunk;
import com.avaya.jtapi.tsapi.impl.core.TSCall;
import com.avaya.jtapi.tsapi.impl.core.TSConnection;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.Call;
import javax.telephony.Connection;

public class TsapiTrunkImpl extends TsapiTrunk {
	TSTrunk tsTrunk;
	String name;

	public String getName() {
		TsapiTrace.traceEntry("getName[]", this);
		TsapiTrace.traceExit("getName[]", this);
		return this.name;
	}

	public int getState() {
		TsapiTrace.traceEntry("getState[]", this);
		TsapiTrace.traceExit("getState[]", this);
		return this.tsTrunk.getState();
	}

	public int getType() {
		TsapiTrace.traceEntry("getType[]", this);
		int type = this.tsTrunk.getType();
		TsapiTrace.traceExit("getType[]", this);
		return type;
	}

	public Call getCall() {
		TsapiTrace.traceEntry("getCall[]", this);
		TSCall tsCall = this.tsTrunk.getTSCall();
		Call call = null;
		if (tsCall != null) {
			call = (Call) TsapiCreateObject.getTsapiObject(tsCall, false);
		}
		TsapiTrace.traceExit("getCall[]", this);
		return call;
	}

	public int hashCode() {
		return this.tsTrunk.hashCode();
	}

	public boolean equals(Object obj) {
		if ((obj instanceof TsapiTrunkImpl)) {
			return this.tsTrunk.equals(((TsapiTrunkImpl) obj).tsTrunk);
		}

		return false;
	}

	TsapiTrunkImpl(TSProviderImpl tsProvider, String trkName) {
		this.tsTrunk = tsProvider.createTSTrunk(trkName);
		if (this.tsTrunk == null) {
			throw new TsapiPlatformException(4, 0, "could not create trunk");
		}
		this.name = trkName;
		TsapiTrace.traceConstruction(this, TsapiTrunkImpl.class);
	}

	TsapiTrunkImpl(TSTrunk _tsTrunk) {
		this.tsTrunk = _tsTrunk;

		this.name = this.tsTrunk.getName();
		TsapiTrace.traceConstruction(this, TsapiTrunkImpl.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiTrunkImpl.class);
	}

	public TSTrunk getTSTrunk() {
		TsapiTrace.traceEntry("getTSTrunk[]", this);
		TsapiTrace.traceExit("getTSTrunk[]", this);
		return this.tsTrunk;
	}

	public Connection getConnection() {
		TsapiTrace.traceEntry("getConnection[]", this);
		TSConnection tsconn = this.tsTrunk.getTSConnection();
		Connection conn = null;
		if (tsconn != null) {
			conn = (Connection) TsapiCreateObject.getTsapiObject(tsconn, true);
		}
		TsapiTrace.traceEntry("getConnection[]", this);
		return conn;
	}

	public String getGroupName() {
		TsapiTrace.traceEntry("getGroupName[]", this);
		String name = this.tsTrunk.getGroupName();
		TsapiTrace.traceExit("getGroupName[]", this);
		return name;
	}

	public String getMemberName() {
		TsapiTrace.traceEntry("getMemberName[]", this);
		String name = this.tsTrunk.getMemberName();
		TsapiTrace.traceExit("getMemberName[]", this);
		return name;
	}

	public static String makeTrunkName(String groupName, String memberName) {
		TsapiTrace.traceEntry(
				"makeTrunkName[String groupName, String memberName]", null);
		String name = null;
		if (groupName == null) {
			name = null;
		} else if (memberName == null) {
			name = groupName + ":0";
		} else {
			name = groupName + ":" + memberName;
		}
		TsapiTrace.traceExit(
				"makeTrunkName[String groupName, String memberName]", null);
		return name;
	}
}