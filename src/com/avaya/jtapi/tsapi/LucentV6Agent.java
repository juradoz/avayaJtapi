package com.avaya.jtapi.tsapi;

public abstract interface LucentV6Agent extends LucentAgent {
	public abstract boolean setState(int paramInt1, int paramInt2,
			int paramInt3, boolean paramBoolean)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV6Agent JD-Core Version: 0.5.4
 */