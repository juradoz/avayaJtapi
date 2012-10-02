package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.Address;
import javax.telephony.Connection;
import javax.telephony.TerminalConnection;
import javax.telephony.callcenter.capabilities.AgentTerminalCapabilities;
import javax.telephony.callcontrol.capabilities.CallControlTerminalCapabilities;
import javax.telephony.capabilities.TerminalCapabilities;

public final class TsapiTerminalCapabilities implements TerminalCapabilities,
		CallControlTerminalCapabilities, AgentTerminalCapabilities {
	private TSCapabilities tsCaps = null;

	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		boolean can = this.tsCaps.getMonitorDevice() == 1;
		TsapiTrace.traceExit("isObservable[]", this);
		return can;
	}

	public boolean canGetDoNotDisturb() {
		TsapiTrace.traceEntry("canGetDoNotDisturb[]", this);
		boolean can = (this.tsCaps.getDoNotDisturbEvent() == 1)
				|| (this.tsCaps.getQueryDnd() == 1);
		TsapiTrace.traceExit("canGetDoNotDisturb[]", this);
		return can;
	}

	public boolean canSetDoNotDisturb() {
		TsapiTrace.traceEntry("canSetDoNotDisturb[]", this);
		boolean can = this.tsCaps.getSetDnd() == 1;
		TsapiTrace.traceExit("canSetDoNotDisturb[]", this);
		return can;
	}

	public boolean canPickup() {
		TsapiTrace.traceEntry("canPickup[]", this);
		boolean can = this.tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit("canPickup[]", this);
		return can;
	}

	public boolean canPickup(Connection connection, Address address) {
		TsapiTrace.traceEntry(
				"canPickup[Connection connection, Address address]", this);
		boolean can = this.tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit(
				"canPickup[Connection connection, Address address]", this);
		return can;
	}

	public boolean canPickup(TerminalConnection tc, Address address) {
		TsapiTrace.traceEntry(
				"canPickup[TerminalConnection tc, Address address]", this);
		boolean can = this.tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit(
				"canPickup[TerminalConnection tc, Address address]", this);
		return can;
	}

	public boolean canPickup(Address address1, Address address2) {
		TsapiTrace.traceEntry("canPickup[Address address1, Address address2]",
				this);
		boolean can = this.tsCaps.getPickupCall() == 1;
		TsapiTrace.traceExit("canPickup[Address address1, Address address2]",
				this);
		return can;
	}

	public boolean canPickupFromGroup() {
		TsapiTrace.traceEntry("canPickupFromGroup[]", this);
		boolean can = canPickupFromGroup((Address) null);
		TsapiTrace.traceExit("canPickupFromGroup[]", this);
		return can;
	}

	public boolean canPickupFromGroup(String group, Address address) {
		TsapiTrace.traceEntry(
				"canPickupFromGroup[String group, Address address]", this);
		TsapiTrace.traceExit(
				"canPickupFromGroup[String group, Address address]", this);
		return false;
	}

	public boolean canPickupFromGroup(Address address) {
		TsapiTrace.traceEntry("canPickupFromGroup[Address address]", this);
		boolean can = this.tsCaps.getGroupPickupCall() == 1;
		TsapiTrace.traceExit("canPickupFromGroup[Address address]", this);
		return can;
	}

	public boolean canHandleAgents() {
		TsapiTrace.traceEntry("canHandleAgents[]", this);
		boolean can = (this.tsCaps.getSetAgentState() == 1)
				&& (this.tsCaps.getQueryAgentState() == 1);
		TsapiTrace.traceExit("canHandleAgents[]", this);
		return can;
	}

	public TsapiTerminalCapabilities(TSCapabilities _tsCaps) {
		this.tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiTerminalCapabilities.class);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiTerminalCapabilities.class);
	}
}