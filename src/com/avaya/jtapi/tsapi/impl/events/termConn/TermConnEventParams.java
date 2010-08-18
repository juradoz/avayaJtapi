package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.TerminalConnection;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class TermConnEventParams extends CallEventParams {
	private TerminalConnection termConn;

	public TermConnEventParams() {
		termConn = null;
	}

	public TerminalConnection getTermConn() {
		return termConn;
	}

	public void setTermConn(final TerminalConnection termConn) {
		this.termConn = termConn;
	}
}
