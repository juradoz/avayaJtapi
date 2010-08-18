package com.avaya.jtapi.tsapi;

public abstract interface V5OriginalCallInfo extends OriginalCallInfo {
	public abstract boolean canSetBillRate();

	public abstract int getCallOriginatorType();

	public abstract String getUCID();

	public abstract boolean hasCallOriginatorType();
}
