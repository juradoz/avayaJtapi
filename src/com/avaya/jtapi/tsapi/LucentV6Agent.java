package com.avaya.jtapi.tsapi;

public abstract interface LucentV6Agent extends LucentAgent {
	public abstract boolean setState(int paramInt1, int paramInt2,
			int paramInt3, boolean paramBoolean)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}
