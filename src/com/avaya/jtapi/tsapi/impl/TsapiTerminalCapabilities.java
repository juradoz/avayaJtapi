package com.avaya.jtapi.tsapi.impl;

import javax.telephony.Address;
import javax.telephony.Connection;
import javax.telephony.TerminalConnection;
import javax.telephony.callcenter.capabilities.AgentTerminalCapabilities;
import javax.telephony.callcontrol.capabilities.CallControlTerminalCapabilities;
import javax.telephony.capabilities.TerminalCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiTerminalCapabilities implements TerminalCapabilities,
		CallControlTerminalCapabilities, AgentTerminalCapabilities {
	private TSCapabilities tsCaps = null;

	public TsapiTerminalCapabilities(final TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiTerminalCapabilities.class);
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
	public boolean canHandleAgents() {
		TsapiTrace.traceEntry("canHandleAgents[]", this);
		final boolean can = tsCaps.getSetAgentState() == 1
				&& tsCaps.getQueryAgentState() == 1;
		TsapiTrace.traceExit("canHandleAgents[]", this);
		return can;
	}

	@Override
	public boolean canPickup() {
		TsapiTrace.traceEntry("canPickup[]", this);
		final boolean can = tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit("canPickup[]", this);
		return can;
	}

	@Override
	public boolean canPickup(final Address address1, final Address address2) {
		TsapiTrace.traceEntry("canPickup[Address address1, Address address2]",
				this);
		final boolean can = tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit("canPickup[Address address1, Address address2]",
				this);
		return can;
	}

	@Override
	public boolean canPickup(final Connection connection, final Address address) {
		TsapiTrace.traceEntry(
				"canPickup[Connection connection, Address address]", this);
		final boolean can = tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit(
				"canPickup[Connection connection, Address address]", this);
		return can;
	}

	@Override
	public boolean canPickup(final TerminalConnection tc, final Address address) {
		TsapiTrace.traceEntry(
				"canPickup[TerminalConnection tc, Address address]", this);
		final boolean can = tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit(
				"canPickup[TerminalConnection tc, Address address]", this);
		return can;
	}

	@Override
	public boolean canPickupFromGroup() {
		TsapiTrace.traceEntry("canPickupFromGroup[]", this);
		final boolean can = canPickupFromGroup((Address) null);
		TsapiTrace.traceExit("canPickupFromGroup[]", this);
		return can;
	}

	@Override
	public boolean canPickupFromGroup(final Address address) {
		TsapiTrace.traceEntry("canPickupFromGroup[Address address]", this);
		final boolean can = tsCaps.getGroupPickupCall() == 1;
		TsapiTrace.traceExit("canPickupFromGroup[Address address]", this);
		return can;
	}

	@Override
	public boolean canPickupFromGroup(final String group, final Address address) {
		TsapiTrace.traceEntry(
				"canPickupFromGroup[String group, Address address]", this);
		TsapiTrace.traceExit(
				"canPickupFromGroup[String group, Address address]", this);
		return false;
	}

	@Override
	public boolean canSetDoNotDisturb() {
		TsapiTrace.traceEntry("canSetDoNotDisturb[]", this);
		final boolean can = tsCaps.getSetDnd() == 1;
		TsapiTrace.traceExit("canSetDoNotDisturb[]", this);
		return can;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiTerminalCapabilities.class);
	}

	@Override
	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		final boolean can = tsCaps.getMonitorDevice() == 1;
		TsapiTrace.traceExit("isObservable[]", this);
		return can;
	}
}
