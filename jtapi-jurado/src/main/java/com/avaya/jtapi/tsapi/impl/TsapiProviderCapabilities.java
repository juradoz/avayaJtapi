package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.callcenter.capabilities.CallCenterProviderCapabilities;
import javax.telephony.capabilities.ProviderCapabilities;

public final class TsapiProviderCapabilities implements ProviderCapabilities,
		CallCenterProviderCapabilities {
	private TSCapabilities tsCaps = null;

	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		TsapiTrace.traceExit("isObservable[]", this);
		return true;
	}

	public boolean canGetRouteableAddresses() {
		TsapiTrace.traceEntry("canGetRouteableAddresses[]", this);
		TsapiTrace.traceExit("canGetRouteableAddresses[]", this);
		return true;
	}

	public boolean canGetACDAddresses() {
		TsapiTrace.traceEntry("canGetACDAddresses[]", this);
		boolean can = this.tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDAddresses[]", this);
		return can;
	}

	public boolean canGetACDManagerAddresses() {
		TsapiTrace.traceEntry("canGetACDManagerAddresses[]", this);
		boolean can = this.tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDManagerAddresses[]", this);
		return can;
	}

	public TsapiProviderCapabilities(TSCapabilities _tsCaps) {
		this.tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiProviderCapabilities.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiProviderCapabilities.class);
	}
}