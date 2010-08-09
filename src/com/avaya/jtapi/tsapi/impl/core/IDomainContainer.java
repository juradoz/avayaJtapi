package com.avaya.jtapi.tsapi.impl.core;

abstract interface IDomainContainer {
	public abstract IDomainCall getDomainCall(int paramInt);

	public abstract IDomainDevice getDomainDevice(String paramString);

	public abstract void logln(String paramString);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.IDomainContainer JD-Core Version: 0.5.4
 */