package com.avaya.jtapi.tsapi;

import javax.telephony.Connection;

public abstract interface LucentV10Call extends LucentV7Call {
	public abstract Connection[] consult(
			LucentTerminalConnection paramLucentTerminalConnection,
			String paramString, boolean paramBoolean,
			UserToUserInfo paramUserToUserInfo, short paramShort)
			throws TsapiInvalidStateException, TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiPrivilegeViolationException;
}