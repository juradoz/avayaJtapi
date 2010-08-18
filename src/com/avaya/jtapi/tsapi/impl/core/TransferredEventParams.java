package com.avaya.jtapi.tsapi.impl.core;

import java.util.ArrayList;

public class TransferredEventParams {
	private ArrayList<TSCall> oldCalls;

	public TransferredEventParams() {
	}

	public TransferredEventParams(final ArrayList<TSCall> oldCalls) {
		this.oldCalls = oldCalls;
	}

	public ArrayList<TSCall> getOldCalls() {
		return oldCalls;
	}

	public void setOldCalls(final ArrayList<TSCall> oldCalls) {
		this.oldCalls = oldCalls;
	}
}
