package com.avaya.jtapi.tsapi;

public abstract interface LucentConnection extends ITsapiConnection {
	public static final short DR_NONE = -1;
	public static final short DR_CALL_CLASSIFIER = 0;
	public static final short DR_TONE_GENERATOR = 1;

	public abstract void disconnect(short paramShort,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiPrivilegeViolationException,
			TsapiResourceUnavailableException,
			TsapiMethodNotSupportedException, TsapiInvalidStateException;
}

