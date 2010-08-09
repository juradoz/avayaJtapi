package com.avaya.jtapi.tsapi.impl;

import javax.telephony.callcenter.capabilities.CallCenterProviderCapabilities;
import javax.telephony.capabilities.ProviderCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiProviderCapabilities implements ProviderCapabilities,
		CallCenterProviderCapabilities {
	private TSCapabilities tsCaps = null;

	public TsapiProviderCapabilities(TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiProviderCapabilities.class);
	}

	public boolean canGetACDAddresses() {
		TsapiTrace.traceEntry("canGetACDAddresses[]", this);
		boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDAddresses[]", this);
		return can;
	}

	public boolean canGetACDManagerAddresses() {
		TsapiTrace.traceEntry("canGetACDManagerAddresses[]", this);
		boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canGetACDManagerAddresses[]", this);
		return can;
	}

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

	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		TsapiTrace.traceExit("isObservable[]", this);
		return true;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiProviderCapabilities JD-Core Version: 0.5.4
 */