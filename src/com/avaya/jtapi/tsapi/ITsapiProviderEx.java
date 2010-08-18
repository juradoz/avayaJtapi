package com.avaya.jtapi.tsapi;

public abstract interface ITsapiProviderEx extends ITsapiProvider {
	public abstract String getServerID();

	public abstract void setHeartbeatInterval(short paramShort)
			throws TsapiInvalidArgumentException;
}
