package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import javax.telephony.TerminalConnection;

public class TermConnEventParams extends CallEventParams {
	private TerminalConnection termConn = null;

	public TerminalConnection getTermConn() {
		return this.termConn;
	}

	public void setTermConn(TerminalConnection termConn) {
		this.termConn = termConn;
	}
}