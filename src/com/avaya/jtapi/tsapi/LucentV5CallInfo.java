package com.avaya.jtapi.tsapi;

public abstract interface LucentV5CallInfo extends LucentCallInfo {
	public abstract boolean canSetBillRate();

	public abstract int getCallOriginatorType();

	public abstract String getUCID();

	public abstract boolean hasCallOriginatorType();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV5CallInfo JD-Core Version: 0.5.4
 */