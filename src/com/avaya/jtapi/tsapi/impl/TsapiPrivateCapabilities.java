package com.avaya.jtapi.tsapi.impl;

import javax.telephony.privatedata.capabilities.PrivateDataCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

final class TsapiPrivateCapabilities implements PrivateDataCapabilities {
	TSCapabilities tsCaps = null;

	TsapiPrivateCapabilities(final TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiPrivateCapabilities.class);
	}

	public boolean canGetPrivateData() {
		TsapiTrace.traceEntry("canGetPrivateData[]", this);
		TsapiTrace.traceExit("canGetPrivateData[]", this);
		return true;
	}

	public boolean canSendPrivateData() {
		TsapiTrace.traceEntry("canSendPrivateData[]", this);
		TsapiTrace.traceExit("canSendPrivateData[]", this);
		return true;
	}

	public boolean canSetPrivateData() {
		TsapiTrace.traceEntry("canSetPrivateData[]", this);
		TsapiTrace.traceExit("canSetPrivateData[]", this);
		return true;
	}
}
