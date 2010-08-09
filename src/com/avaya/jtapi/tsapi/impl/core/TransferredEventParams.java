package com.avaya.jtapi.tsapi.impl.core;

import java.util.ArrayList;

public class TransferredEventParams {
	private ArrayList<TSCall> oldCalls;

	public TransferredEventParams() {
	}

	public TransferredEventParams(ArrayList<TSCall> oldCalls) {
		this.oldCalls = oldCalls;
	}

	public ArrayList<TSCall> getOldCalls() {
		return oldCalls;
	}

	public void setOldCalls(ArrayList<TSCall> oldCalls) {
		this.oldCalls = oldCalls;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.TransferredEventParams JD-Core Version: 0.5.4
 */