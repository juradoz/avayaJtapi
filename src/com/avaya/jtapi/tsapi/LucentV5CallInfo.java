package com.avaya.jtapi.tsapi;

public abstract interface LucentV5CallInfo extends LucentCallInfo {
	public abstract String getUCID();

	public abstract int getCallOriginatorType();

	public abstract boolean hasCallOriginatorType();

	public abstract boolean canSetBillRate();
}