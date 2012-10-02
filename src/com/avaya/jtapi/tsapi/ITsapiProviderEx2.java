package com.avaya.jtapi.tsapi;

public abstract interface ITsapiProviderEx2 extends ITsapiProviderEx {
	public abstract String requestPrivileges() throws TsapiPlatformException;

	public abstract void setPrivileges(String paramString)
			throws TsapiPlatformException, TsapiInvalidArgumentException;
}