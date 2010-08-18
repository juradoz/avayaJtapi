package com.avaya.jtapi.tsapi;

public abstract interface LucentTerminalConnection extends
		ITsapiTerminalConnection {
	public static final short DR_NONE = -1;
	public static final short DR_CALL_CLASSIFIER = 0;
	public static final short DR_TONE_GENERATOR = 1;

	public abstract void leave(short paramShort,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiInvalidStateException,
			TsapiMethodNotSupportedException, TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException;
}

