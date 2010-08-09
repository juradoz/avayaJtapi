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

	public void setTermConn(TerminalConnection termConn) {
		this.termConn = termConn;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.TermConnEventParams JD-Core
 * Version: 0.5.4
 */