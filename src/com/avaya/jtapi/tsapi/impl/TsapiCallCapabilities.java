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

	public TsapiCallCapabilities(TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiCallCapabilities.class);
	}

	public boolean canAddParty() {
		TsapiTrace.traceEntry("canAddParty[]", this);
		boolean can = tsCaps.getAddParty() == 1;
		TsapiTrace.traceExit("canAddParty[]", this);
		return can;
	}

	public boolean canConference() {
		TsapiTrace.traceEntry("canConference[]", this);
		boolean can = tsCaps.getConferenceCall() == 1;
		TsapiTrace.traceExit("canConference[]", this);
		return can;
	}

	public boolean canConnect() {
		TsapiTrace.traceEntry("canConnect[]", this);
		boolean can = tsCaps.getMakeCall() == 1;
		TsapiTrace.traceExit("canConnect[]", this);
		return can;
	}

	public boolean canConnectPredictive() {
		TsapiTrace.traceEntry("canConnectPredictive[]", this);
		boolean can = tsCaps.getMakePredictiveCall() == 1;
		TsapiTrace.traceExit("canConnectPredictive[]", this);
		return can;
	}

	public boolean canConsult() {
		TsapiTrace.traceEntry("canConsult[]", this);
		boolean can = canConsult((TerminalConnection) null, (String) null);
		TsapiTrace.traceExit("canConsult[]", this);
		return can;
	}

	public boolean canConsult(TerminalConnection tc) {
		TsapiTrace.traceEntry("canConsult[TerminalConnection tc]", this);
		TsapiTrace.traceExit("canConsult[TerminalConnection tc]", this);
		return false;
	}

	public boolean canConsult(TerminalConnection tc, String destination) {
		TsapiTrace.traceEntry(
				"canConsult[TerminalConnection tc, String destination]", this);
		boolean can = tsCaps.getConsultationCall() == 1;
		TsapiTrace.traceExit(
				"canConsult[TerminalConnection tc, String destination]", this);
		return can;
	}

	public boolean canDrop() {
		TsapiTrace.traceEntry("canDrop[]", this);
		boolean can = tsCaps.getClearCall() == 1;
		TsapiTrace.traceExit("canDrop[]", this);
		return can;
	}

	public boolean canGetTrunks() {
		TsapiTrace.traceEntry("canGetTrunks[]", this);
		boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canGetTrunks[]", this);
		return can;
	}

	public boolean canHandleApplicationData() {
		TsapiTrace.traceEntry("canHandleApplicationData[]", this);
		TsapiTrace.traceExit("canHandleApplicationData[]", this);
		return false;
	}

	public boolean canOffHook() {
		TsapiTrace.traceEntry("canOffHook[]", this);
		TsapiTrace.traceExit("canOffHook[]", this);
		return false;
	}

	public boolean canSetConferenceController() {
		TsapiTrace.traceEntry("canSetConferenceController[]", this);
		TsapiTrace.traceExit("canSetConferenceController[]", this);
		return true;
	}

	public boolean canSetConferenceEnable() {
		TsapiTrace.traceEntry("canSetConferenceEnable[]", this);
		TsapiTrace.traceExit("canSetConferenceEnable[]", this);
		return true;
	}

	public boolean canSetTransferController() {
		TsapiTrace.traceEntry("canSetTransferController[]", this);
		TsapiTrace.traceExit("canSetTransferController[]", this);
		return true;
	}

	public boolean canSetTransferEnable() {
		TsapiTrace.traceEntry("canSetTransferEnable[]", this);
		TsapiTrace.traceExit("canSetTransferEnable[]", this);
		return true;
	}

	public boolean canTransfer() {
		TsapiTrace.traceEntry("canTransfer[]", this);
		boolean can = canTransfer((Call) null);
		TsapiTrace.traceExit("canTransfer[]", this);
		return can;
	}

	public boolean canTransfer(Call call) {
		TsapiTrace.traceEntry("canTransfer[Call call]", this);
		boolean can = tsCaps.getTransferCall() == 1;
		TsapiTrace.traceExit("canTransfer[Call call]", this);
		return can;
	}

	public boolean canTransfer(String destination) {
		TsapiTrace.traceEntry("canTransfer[String destination]", this);
		if (tsCaps.isLucent()) {
			boolean isV8 = tsCaps.isLucentV8();
			TsapiTrace.traceExit("canTransfer[String destination]", this);

			return isV8;
		}

		boolean can = tsCaps.getTransferCall() == 1;
		TsapiTrace.traceExit("canTransfer[String destination]", this);
		return can;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiCallCapabilities.class);
	}

	public boolean isObservable() {
		TsapiTrace.traceEntry("isObservable[]", this);
		boolean can = tsCaps.getMonitorCall() == 1;
		TsapiTrace.traceExit("isObservable[]", this);
		return can;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.TsapiCallCapabilities JD-Core Version: 0.5.4
 */