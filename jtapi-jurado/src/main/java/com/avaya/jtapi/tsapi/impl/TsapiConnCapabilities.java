package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.callcenter.capabilities.ACDConnectionCapabilities;
import javax.telephony.callcenter.capabilities.ACDManagerConnectionCapabilities;
import javax.telephony.callcontrol.capabilities.CallControlConnectionCapabilities;
import javax.telephony.capabilities.ConnectionCapabilities;

public final class TsapiConnCapabilities implements ConnectionCapabilities,
		CallControlConnectionCapabilities, ACDConnectionCapabilities,
		ACDManagerConnectionCapabilities {
	private TSCapabilities tsCaps = null;

	public boolean canDisconnect() {
		TsapiTrace.traceEntry("canDisconnect[]", this);
		boolean can = this.tsCaps.getClearConnection() == 1;
		TsapiTrace.traceExit("canDisconnect[]", this);
		return can;
	}

	public boolean canRedirect() {
		TsapiTrace.traceEntry("canRedirect[]", this);
		boolean can = this.tsCaps.getDeflectCall() == 1;
		TsapiTrace.traceExit("canRedirect[]", this);
		return can;
	}

	public boolean canAddToAddress() {
		TsapiTrace.traceEntry("canAddToAddress[]", this);
		TsapiTrace.traceExit("canAddToAddress[]", this);
		return false;
	}

	public boolean canAccept() {
		TsapiTrace.traceEntry("canAccept[]", this);
		TsapiTrace.traceExit("canAccept[]", this);
		return false;
	}

	public boolean canReject() {
		TsapiTrace.traceEntry("canReject[]", this);
		TsapiTrace.traceExit("canReject[]", this);
		return false;
	}

	public boolean canPark() {
		TsapiTrace.traceEntry("canPark[]", this);
		TsapiTrace.traceExit("canPark[]", this);
		return false;
	}

	public boolean canGetACDManagerConnection() {
		TsapiTrace.traceEntry("canGetACDManagerConnection[]", this);
		boolean can = this.tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDManagerConnection[]", this);
		return can;
	}

	public boolean canGetACDConnections() {
		TsapiTrace.traceEntry("canGetACDConnections[]", this);
		boolean can = this.tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDConnections[]", this);
		return can;
	}

	public TsapiConnCapabilities(TSCapabilities _tsCaps) {
		this.tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiConnCapabilities.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiConnCapabilities.class);
	}
}