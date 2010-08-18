package com.avaya.jtapi.tsapi;

public abstract interface LucentV5CallInfo extends LucentCallInfo {
	public abstract boolean canSetBillRate();

	public abstract int getCallOriginatorType();

	public abstract String getUCID();

	public abstract boolean hasCallOriginatorType();
}

