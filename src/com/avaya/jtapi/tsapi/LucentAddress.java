package com.avaya.jtapi.tsapi;

public abstract interface LucentAddress extends ITsapiAddress {
	public static final int MWI_MCS = 16777216;
	public static final int MWI_VOICE = 33554432;
	public static final int MWI_PROPMGT = 67108864;
	public static final int MWI_LWC = 134217728;
	public static final int MWI_CTI = 268435456;

	public abstract String getDirectoryName();

	public abstract int getMessageWaitingBits()
			throws TsapiMethodNotSupportedException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentAddress JD-Core Version: 0.5.4
 */