package com.avaya.jtapi.tsapi;

public abstract interface LucentV5Connection extends LucentConnection {
	public abstract void listenHold(
			LucentTerminalConnection paramLucentTerminalConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;

	public abstract void listenUnhold(
			LucentTerminalConnection paramLucentTerminalConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;

	public abstract void listenHold(LucentConnection paramLucentConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;

	public abstract void listenUnhold(LucentConnection paramLucentConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;
}