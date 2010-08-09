package com.avaya.jtapi.tsapi;

public abstract interface V5OriginalCallInfo extends OriginalCallInfo {
	public abstract boolean canSetBillRate();

	public abstract int getCallOriginatorType();

	public abstract String getUCID();

	public abstract boolean hasCallOriginatorType();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.V5OriginalCallInfo JD-Core Version: 0.5.4
 */