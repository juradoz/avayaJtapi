package com.avaya.jtapi.tsapi.impl.core;

abstract interface IDomainTracker {
	public abstract IDomainDevice addCallToDomain(
			IDomainDevice paramIDomainDevice, IDomainCall paramIDomainCall);

	public abstract void removeCallFromDomain(IDomainCall paramIDomainCall);

	public abstract void removeAllCallsForDomain(
			IDomainDevice paramIDomainDevice);

	public abstract void changeCallIDInDomain(int paramInt1, int paramInt2);

	public abstract IDomainDevice getDomainCallIsIn(IDomainCall paramIDomainCall);

	public abstract boolean isCallInAnyDomain(IDomainCall paramIDomainCall);

	public abstract void dumpDomainData(String paramString);
}