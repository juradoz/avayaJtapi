package com.avaya.jtapi.tsapi.impl.core;

abstract interface IDomainContainer {
	public abstract IDomainCall getDomainCall(int paramInt);

	public abstract IDomainDevice getDomainDevice(String paramString);

	public abstract void logln(String paramString);
}

