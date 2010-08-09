package com.avaya.jtapi.tsapi.impl.core;

abstract interface IDomainTracker {
	public abstract IDomainDevice addCallToDomain(
			IDomainDevice paramIDomainDevice, IDomainCall paramIDomainCall);

	public abstract void changeCallIDInDomain(int paramInt1, int paramInt2);

	public abstract void dumpDomainData(String paramString);

	public abstract IDomainDevice getDomainCallIsIn(IDomainCall paramIDomainCall);

	public abstract boolean isCallInAnyDomain(IDomainCall paramIDomainCall);

	public abstract void removeAllCallsForDomain(
			IDomainDevice paramIDomainDevice);

	public abstract void removeCallFromDomain(IDomainCall paramIDomainCall);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.IDomainTracker JD-Core Version: 0.5.4
 */