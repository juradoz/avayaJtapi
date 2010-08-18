package com.avaya.jtapi.tsapi;

import javax.telephony.Connection;

public abstract interface LucentV5Call extends ITsapiCall, LucentCallEx2,
		LucentV5CallInfo {
	public abstract Connection addParty(String paramString, boolean paramBoolean)
			throws TsapiInvalidStateException, TsapiInvalidPartyException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException;

	public abstract void setBillRate(short paramShort, float paramFloat)
			throws TsapiInvalidArgumentException,
			TsapiMethodNotSupportedException, TsapiResourceUnavailableException;
}
