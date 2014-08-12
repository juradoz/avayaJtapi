package com.avaya.jtapi.tsapi.impl.core;

import java.util.ArrayList;

public class TransferredEventParams {
	private ArrayList<TSCall> oldCalls;

	public ArrayList<TSCall> getOldCalls() {
		return this.oldCalls;
	}

	public TransferredEventParams() {
	}

	public void setOldCalls(ArrayList<TSCall> oldCalls) {
		this.oldCalls = oldCalls;
	}

	public TransferredEventParams(ArrayList<TSCall> oldCalls) {
		this.oldCalls = oldCalls;
	}
}