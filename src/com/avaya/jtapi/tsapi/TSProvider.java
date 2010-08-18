package com.avaya.jtapi.tsapi;

public abstract class TSProvider {
	public abstract void disableHeartbeat();

	public abstract void enableHeartbeat();

	public abstract void finalizeOldProvider();

	public abstract int getLucentPDV();

	public abstract String getServerID();

	public abstract boolean heartbeatIsEnabled();

	public abstract void heartbeatTimeout();

	public abstract void initNewProvider();

	public abstract boolean isLucentV7();

	public abstract boolean isLucentV8();

	public abstract void setHeartbeatInterval(short paramShort)
			throws TsapiInvalidArgumentException;

	public abstract void shutdown();

	@Override
	public abstract String toString();
}
