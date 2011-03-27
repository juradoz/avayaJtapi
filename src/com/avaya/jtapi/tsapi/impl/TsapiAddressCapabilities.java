package com.avaya.jtapi.tsapi.impl;

import javax.telephony.callcenter.capabilities.ACDAddressCapabilities;
import javax.telephony.callcenter.capabilities.ACDManagerAddressCapabilities;
import javax.telephony.callcenter.capabilities.CallCenterAddressCapabilities;
import javax.telephony.callcenter.capabilities.RouteAddressCapabilities;
import javax.telephony.callcontrol.capabilities.CallControlAddressCapabilities;
import javax.telephony.capabilities.AddressCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiAddressCapabilities implements AddressCapabilities,
		CallControlAddressCapabilities, CallCenterAddressCapabilities,
		RouteAddressCapabilities, ACDAddressCapabilities,
		ACDManagerAddressCapabilities {
	TSCapabilities tsCaps = null;

	public TsapiAddressCapabilities(final TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiAddressCapabilities.class);
	}

	@Override
	public boolean canAddCallObserver(final boolean remain) {
		TsapiTrace.traceEntry("canAddCallObserver[boolean remain]", this);
		final boolean can = tsCaps.getMonitorCallsViaDevice() == 1;
		TsapiTrace.traceExit("canAddCallObserver[boolean remain]", this);
		return can;
	}

	@Override
	public boolean canCancelForwarding() {
		TsapiTrace.traceEntry("canCancelForwarding[]", this);
		final boolean can = tsCaps.getSetFwd() == 1;
		TsapiTrace.traceExit("canCancelForwarding[]", this);
		return can;
	}

	@Override
	public boolean canGetACDAddresses() {
		TsapiTrace.traceEntry("canGetACDAddresses[]", this);
		TsapiTrace.traceExit("canGetACDAddresses[]", this);
		return false;
	}

	@Override
	public boolean canGetACDManagerAddress() {
		TsapiTrace.traceEntry("canGetACDManagerAddress[]", this);
		TsapiTrace.traceExit("canGetACDManagerAddress[]", this);
		return false;
	}

	@Override
	public boolean canGetDoNotDisturb() {
		TsapiTrace.traceEntry("canGetDoNotDisturb[]", this);
		final boolean can = tsCaps.getDoNotDisturbEvent() == 1
				|| tsCaps.getQueryDnd() == 1;
		TsapiTrace.traceExit("canGetDoNotDisturb[]", this);
		return can;
	}

	@Override
	public boolean canGetForwarding() {
		TsapiTrace.traceEntry("canGetForwarding[]", this);
		final boolean can = tsCaps.getQueryFwd() == 1;
		TsapiTrace.traceExit("canGetForwarding[]", this);
		return can;
	}

	@Override
	public boolean canGetLoggedOnAgents() {
		TsapiTrace.traceEntry("canGetLoggedOnAgents[]", this);
		TsapiTrace.traceExit("canGetLoggedOnAgents[]", this);
		return true;
	}

	@Override
	public boolean canGetMessageWaiting() {
		TsapiTrace.traceEntry("canGetMessageWaiting[]", this);
		final boolean can = tsCaps.getMessageWaitingEvent() == 1
				|| tsCaps.getQueryMwi() == 1;
		TsapiTrace.traceExit("canGetMessageWaiting[]", this);
		return can;
	}

	@Override
	public boolean canGetNumberQueued() {
		TsapiTrace.traceEntry("canGetNumberQueued[]", this);
		final boolean can = tsCaps.isLucent() || tsCaps.getQueuedEvent() == 1;
		TsapiTrace.traceExit("canGetNumberQueued[]", this);
		return can;
	}

	@Override
	public boolean canGetOldestCallQueued() {
		TsapiTrace.traceEntry("canGetOldestCallQueued[]", this);
		TsapiTrace.traceExit("canGetOldestCallQueued[]", this);
		return false;
	}

	@Override
	public boolean canGetQueueWaitTime() {
		TsapiTrace.traceEntry("canGetQueueWaitTime[]", this);
		TsapiTrace.traceExit("canGetQueueWaitTime[]", this);
		return false;
	}

	@Override
	public boolean canGetRelativeQueueLoad() {
		TsapiTrace.traceEntry("canGetRelativeQueueLoad[]", this);
		TsapiTrace.traceExit("canGetRelativeQueueLoad[]", this);
		return false;
	}

	@Override
	public boolean canRouteCalls() {
		TsapiTrace.traceEntry("canRouteCalls[]", this);
		final boolean can = tsCaps.getRouteRequestEvent() == 1;

		TsapiTrace.traceExit("canRouteCalls[]", this);
		return can;
	}

	@Override
	public boolean canSetDoNotDisturb() {
		TsapiTrace.traceEntry("canSetDoNotDisturb[]", this);
		final boolean can = tsCaps.getSetDnd() == 1;
		TsapiTrace.traceExit("canSetDoNotDisturb[]", this);
		return can;
	}

	@Override
	public boolean canSetForwarding() {
		TsapiTrace.traceEntry("canSetForwarding[]", this);
		final boolean can = tsCaps.getSetFwd() == 1;
		TsapiTrace.traceExit("canSetForwarding[]", this);
		return can;
	}

	@Override
	public boolean canSetMessageWaiting() {
		TsapiTrace.traceEntry("canSetMessageWaiting[]", this);
		final boolean can = tsCaps.getSetMwi() == 1;
		TsapiTrace.traceExit("canSetMessageWaiting[]", this);
		return can;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiAddressCapabilities.class);
	}

	@Override
	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		final boolean is = tsCaps.getMonitorDevice() == 1;
		TsapiTrace.traceExit("isObservable[]", this);
		return is;
	}
}
