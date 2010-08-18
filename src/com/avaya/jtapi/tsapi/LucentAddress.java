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
