package com.avaya.jtapi.tsapi;

import javax.telephony.Address;
import javax.telephony.Connection;
import javax.telephony.Terminal;

public abstract interface LucentCallEx2 extends LucentCallEx {
	public abstract Connection fastConnect(Terminal paramTerminal,
			Address paramAddress, String paramString1, boolean paramBoolean,
			UserToUserInfo paramUserToUserInfo, String paramString2)
			throws TsapiResourceUnavailableException,
			TsapiPrivilegeViolationException, TsapiInvalidPartyException,
			TsapiInvalidArgumentException, TsapiInvalidStateException,
			TsapiMethodNotSupportedException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentCallEx2 JD-Core Version: 0.5.4
 */