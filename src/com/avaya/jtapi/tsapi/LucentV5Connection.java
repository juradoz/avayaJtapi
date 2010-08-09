package com.avaya.jtapi.tsapi;

public abstract interface LucentV5Connection extends LucentConnection {
	public abstract void listenHold(LucentConnection paramLucentConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;

	public abstract void listenHold(
			LucentTerminalConnection paramLucentTerminalConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;

	public abstract void listenUnhold(LucentConnection paramLucentConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;

	public abstract void listenUnhold(
			LucentTerminalConnection paramLucentTerminalConnection)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException, TsapiInvalidArgumentException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV5Connection JD-Core Version: 0.5.4
 */