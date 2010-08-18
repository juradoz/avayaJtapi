package com.avaya.jtapi.tsapi;

public abstract interface ITsapiProviderEx2 extends ITsapiProviderEx {
	public abstract String requestPrivileges()
			throws TsapiInvalidArgumentException;

	public abstract void setPrivileges(String paramString)
			throws TsapiInvalidArgumentException;
}

