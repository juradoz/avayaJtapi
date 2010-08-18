package com.avaya.jtapi.tsapi.impl.core;

abstract interface IDomainCall {
	public abstract int getDomainCallID();

	public abstract void notifyCallAdded(IDomainDevice paramIDomainDevice);

	public abstract void notifyCallRemoved(IDomainDevice paramIDomainDevice);
}

