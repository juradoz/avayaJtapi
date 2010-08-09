package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiCallControlForwarding {
	String destAddress;
	int type;
	int whichCalls;

	public TsapiCallControlForwarding(String _destAddress, int _type) {
		destAddress = _destAddress;
		type = _type;
		whichCalls = 1;
		TsapiTrace.traceConstruction(this, TsapiCallControlForwarding.class);
	}

	public TsapiCallControlForwarding(String _destAddress, int _type,
			boolean internalCalls) {
		destAddress = _destAddress;
		type = _type;
		if (internalCalls) {
			whichCalls = 2;
		} else {
			whichCalls = 3;
		}
		TsapiTrace.traceConstruction(this, TsapiCallControlForwarding.class);
	}

	public boolean equals(TsapiCallControlForwarding other) {
		return (destAddress == other.destAddress) && (type == other.type)
				&& (whichCalls == other.whichCalls);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiCallControlForwarding.class);
	}

	public String getDestinationAddress() {
		TsapiTrace.traceEntry("getDestinationAddress[]", this);
		TsapiTrace.traceExit("getDestinationAddress[]", this);
		return destAddress;
	}

	public int getFilter() {
		TsapiTrace.traceEntry("getFilter[]", this);
		TsapiTrace.traceExit("getFilter[]", this);
		return whichCalls;
	}

	public int getType() {
		TsapiTrace.traceEntry("getType[]", this);
		TsapiTrace.traceExit("getType[]", this);
		return type;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiCallControlForwarding JD-Core Version: 0.5.4
 */