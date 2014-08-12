package com.avaya.jtapi.tsapi;

public abstract interface ITsapiCallEvent {
	public abstract short getCSTACause();

	public abstract short getCSTACause(CSTACauseVariant paramCSTACauseVariant);
}