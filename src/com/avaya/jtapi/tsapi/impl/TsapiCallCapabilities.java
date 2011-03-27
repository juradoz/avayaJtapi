package com.avaya.jtapi.tsapi.impl;

import javax.telephony.Call;
import javax.telephony.TerminalConnection;
import javax.telephony.callcenter.capabilities.CallCenterCallCapabilities;
import javax.telephony.callcontrol.capabilities.CallControlCallCapabilities;
import javax.telephony.capabilities.CallCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiCallCapabilities implements CallCapabilities,
		CallCenterCallCapabilities, CallControlCallCapabilities {
	private TSCapabilities tsCaps = null;

	public TsapiCallCapabilities(final TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiCallCapabilities.class);
	}

	@Override
	public boolean canAddParty() {
		TsapiTrace.traceEntry("canAddParty[]", this);
		final boolean can = tsCaps.getAddParty() == 1;
		TsapiTrace.traceExit("canAddParty[]", this);
		return can;
	}

	@Override
	public boolean canConference() {
		TsapiTrace.traceEntry("canConference[]", this);
		final boolean can = tsCaps.getConferenceCall() == 1;
		TsapiTrace.traceExit("canConference[]", this);
		return can;
	}

	@Override
	public boolean canConnect() {
		TsapiTrace.traceEntry("canConnect[]", this);
		final boolean can = tsCaps.getMakeCall() == 1;
		TsapiTrace.traceExit("canConnect[]", this);
		return can;
	}

	@Override
	public boolean canConnectPredictive() {
		TsapiTrace.traceEntry("canConnectPredictive[]", this);
		final boolean can = tsCaps.getMakePredictiveCall() == 1;
		TsapiTrace.traceExit("canConnectPredictive[]", this);
		return can;
	}

	@Override
	public boolean canConsult() {
		TsapiTrace.traceEntry("canConsult[]", this);
		final boolean can = canConsult((TerminalConnection) null, (String) null);
		TsapiTrace.traceExit("canConsult[]", this);
		return can;
	}

	@Override
	public boolean canConsult(final TerminalConnection tc) {
		TsapiTrace.traceEntry("canConsult[TerminalConnection tc]", this);
		TsapiTrace.traceExit("canConsult[TerminalConnection tc]", this);
		return false;
	}

	@Override
	public boolean canConsult(final TerminalConnection tc,
			final String destination) {
		TsapiTrace.traceEntry(
				"canConsult[TerminalConnection tc, String destination]", this);
		final boolean can = tsCaps.getConsultationCall() == 1;
		TsapiTrace.traceExit(
				"canConsult[TerminalConnection tc, String destination]", this);
		return can;
	}

	@Override
	public boolean canDrop() {
		TsapiTrace.traceEntry("canDrop[]", this);
		final boolean can = tsCaps.getClearCall() == 1;
		TsapiTrace.traceExit("canDrop[]", this);
		return can;
	}

	@Override
	public boolean canGetTrunks() {
		TsapiTrace.traceEntry("canGetTrunks[]", this);
		final boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canGetTrunks[]", this);
		return can;
	}

	@Override
	public boolean canHandleApplicationData() {
		TsapiTrace.traceEntry("canHandleApplicationData[]", this);
		TsapiTrace.traceExit("canHandleApplicationData[]", this);
		return false;
	}

	@Override
	public boolean canOffHook() {
		TsapiTrace.traceEntry("canOffHook[]", this);
		TsapiTrace.traceExit("canOffHook[]", this);
		return false;
	}

	@Override
	public boolean canSetConferenceController() {
		TsapiTrace.traceEntry("canSetConferenceController[]", this);
		TsapiTrace.traceExit("canSetConferenceController[]", this);
		return true;
	}

	@Override
	public boolean canSetConferenceEnable() {
		TsapiTrace.traceEntry("canSetConferenceEnable[]", this);
		TsapiTrace.traceExit("canSetConferenceEnable[]", this);
		return true;
	}

	@Override
	public boolean canSetTransferController() {
		TsapiTrace.traceEntry("canSetTransferController[]", this);
		TsapiTrace.traceExit("canSetTransferController[]", this);
		return true;
	}

	@Override
	public boolean canSetTransferEnable() {
		TsapiTrace.traceEntry("canSetTransferEnable[]", this);
		TsapiTrace.traceExit("canSetTransferEnable[]", this);
		return true;
	}

	@Override
	public boolean canTransfer() {
		TsapiTrace.traceEntry("canTransfer[]", this);
		final boolean can = canTransfer((Call) null);
		TsapiTrace.traceExit("canTransfer[]", this);
		return can;
	}

	@Override
	public boolean canTransfer(final Call call) {
		TsapiTrace.traceEntry("canTransfer[Call call]", this);
		final boolean can = tsCaps.getTransferCall() == 1;
		TsapiTrace.traceExit("canTransfer[Call call]", this);
		return can;
	}

	@Override
	public boolean canTransfer(final String destination) {
		TsapiTrace.traceEntry("canTransfer[String destination]", this);
		if (tsCaps.isLucent()) {
			final boolean isV8 = tsCaps.isLucentV8();
			TsapiTrace.traceExit("canTransfer[String destination]", this);

			return isV8;
		}

		final boolean can = tsCaps.getTransferCall() == 1;
		TsapiTrace.traceExit("canTransfer[String destination]", this);
		return can;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiCallCapabilities.class);
	}

	@Override
	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		final boolean can = tsCaps.getMonitorCall() == 1;
		TsapiTrace.traceExit("isObservable[]", this);
		return can;
	}
}
