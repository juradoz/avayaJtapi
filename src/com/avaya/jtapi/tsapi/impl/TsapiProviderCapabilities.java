package com.avaya.jtapi.tsapi.impl;

import javax.telephony.callcenter.capabilities.CallCenterProviderCapabilities;
import javax.telephony.capabilities.ProviderCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiProviderCapabilities implements ProviderCapabilities,
		CallCenterProviderCapabilities {
	private TSCapabilities tsCaps = null;

	public TsapiProviderCapabilities(final TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiProviderCapabilities.class);
	}

	@Override
	public boolean canGetACDAddresses() {
		TsapiTrace.traceEntry("canGetACDAddresses[]", this);
		final boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDAddresses[]", this);
		return can;
	}

	@Override
	public boolean canGetACDManagerAddresses() {
		TsapiTrace.traceEntry("canGetACDManagerAddresses[]", this);
		final boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDManagerAddresses[]", this);
		return can;
	}

	@Override
	public boolean canGetRouteableAddresses() {
		TsapiTrace.traceEntry("canGetRouteableAddresses[]", this);
		TsapiTrace.traceExit("canGetRouteableAddresses[]", this);
		return true;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiProviderCapabilities.class);
	}

	@Override
	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		TsapiTrace.traceExit("isObservable[]", this);
		return true;
	}
}
