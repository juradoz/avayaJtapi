package com.avaya.jtapi.tsapi.impl;

import javax.telephony.callcontrol.capabilities.CallControlTerminalConnectionCapabilities;
import javax.telephony.capabilities.TerminalConnectionCapabilities;

import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
import com.avaya.jtapi.tsapi.util.TsapiTrace;

public final class TsapiTermConnCapabilities implements
		TerminalConnectionCapabilities,
		CallControlTerminalConnectionCapabilities {
	private TSCapabilities tsCaps = null;

	public TsapiTermConnCapabilities(TSCapabilities _tsCaps) {
		tsCaps = _tsCaps;
		TsapiTrace.traceConstruction(this, TsapiTermConnCapabilities.class);
	}

	public boolean canAnswer() {
		TsapiTrace.traceEntry("canAnswer[]", this);
		boolean can = tsCaps.getAnswerCall() == 1;
		TsapiTrace.traceExit("canAnswer[]", this);
		return can;
	}

	public boolean canDetectDtmf() {
		TsapiTrace.traceEntry("canDetectDtmf[]", this);
		TsapiTrace.traceExit("canDetectDtmf[]", this);
		return false;
	}

	public boolean canGenerateDtmf() {
		TsapiTrace.traceEntry("canGenerateDtmf[]", this);
		boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canGenerateDtmf[]", this);
		return can;
	}

	public boolean canHold() {
		TsapiTrace.traceEntry("canHold[]", this);
		boolean can = tsCaps.getHoldCall() == 1;
		TsapiTrace.traceExit("canHold[]", this);
		return can;
	}

	public boolean canJoin() {
		TsapiTrace.traceEntry("canJoin[]", this);
		boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canJoin[]", this);
		return can;
	}

	public boolean canLeave() {
		TsapiTrace.traceEntry("canLeave[]", this);
		boolean can = tsCaps.isLucent();
		TsapiTrace.traceExit("canLeave[]", this);
		return can;
	}

	public boolean canStartPlaying() {
		TsapiTrace.traceEntry("canStartPlaying[]", this);
		TsapiTrace.traceExit("canStartPlaying[]", this);
		return false;
	}

	public boolean canStartRecording() {
		TsapiTrace.traceEntry("canStartRecording[]", this);
		TsapiTrace.traceExit("canStartRecording[]", this);
		return false;
	}

	public boolean canStopPlaying() {
		TsapiTrace.traceEntry("canStopPlaying[]", this);
		TsapiTrace.traceExit("canStopPlaying[]", this);
		return false;
	}

	public boolean canStopRecording() {
		TsapiTrace.traceEntry("canStopRecording[]", this);
		TsapiTrace.traceExit("canStopRecording[]", this);
		return false;
	}

	public boolean canUnhold() {
		TsapiTrace.traceEntry("canUnhold[]", this);
		boolean can = tsCaps.getRetrieveCall() == 1;
		TsapiTrace.traceExit("canUnhold[]", this);
		return can;
	}

	public boolean canUseDefaultMicrophone() {
		TsapiTrace.traceEntry("canUseDefaultMicrophone[]", this);
		TsapiTrace.traceExit("canUseDefaultMicrophone[]", this);
		return false;
	}

	public boolean canUseDefaultSpeaker() {
		TsapiTrace.traceEntry("canUseDefaultSpeaker[]", this);
		TsapiTrace.traceExit("canUseDefaultSpeaker[]", this);
		return false;
	}

	public boolean canUsePlayURL() {
		TsapiTrace.traceEntry("canUsePlayURL[]", this);
		TsapiTrace.traceExit("canUsePlayURL[]", this);
		return false;
	}

	public boolean canUseRecordURL() {
		TsapiTrace.traceEntry("canUseRecordURL[]", this);
		TsapiTrace.traceExit("canUseRecordURL[]", this);
		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		TsapiTrace.traceDestruction(this, TsapiTermConnCapabilities.class);
	}
}

