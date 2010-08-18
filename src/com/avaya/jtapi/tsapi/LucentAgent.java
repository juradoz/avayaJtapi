package com.avaya.jtapi.tsapi;

public abstract interface LucentAgent extends ITsapiAgent {
	public static final int MODE_NONE = 0;
	public static final int MODE_AUTO_IN = 1;
	public static final int MODE_MANUAL_IN = 2;

	public abstract LucentAgentStateInfoEx getStateInfo();

	public abstract void setState(int paramInt1, int paramInt2)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}

