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

