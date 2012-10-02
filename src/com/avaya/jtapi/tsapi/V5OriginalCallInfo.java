package com.avaya.jtapi.tsapi;

public abstract interface V5OriginalCallInfo extends OriginalCallInfo {
	public abstract String getUCID();

	public abstract int getCallOriginatorType();

	public abstract boolean hasCallOriginatorType();

	public abstract boolean canSetBillRate();
}