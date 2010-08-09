package com.avaya.jtapi.tsapi;

public abstract interface LucentAgent extends ITsapiAgent {
	public static final int MODE_NONE = 0;
	public static final int MODE_AUTO_IN = 1;
	public static final int MODE_MANUAL_IN = 2;

	public abstract LucentAgentStateInfoEx getStateInfo();

	public abstract void setState(int paramInt1, int paramInt2)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentAgent JD-Core Version: 0.5.4
 */