package com.avaya.jtapi.tsapi;

public abstract interface ITsapiProviderEx2 extends ITsapiProviderEx {
	public abstract String requestPrivileges()
			throws TsapiInvalidArgumentException;

	public abstract void setPrivileges(String paramString)
			throws TsapiInvalidArgumentException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.ITsapiProviderEx2 JD-Core Version: 0.5.4
 */