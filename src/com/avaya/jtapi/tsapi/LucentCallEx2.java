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
