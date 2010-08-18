package com.avaya.jtapi.tsapi;

public abstract interface LucentRouteSession extends ITsapiRouteSession {
	public abstract void selectRoute(String paramString, boolean paramBoolean,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiMethodNotSupportedException;

	public abstract void selectRouteAndCollect(String paramString,
			int paramInt1, int paramInt2, boolean paramBoolean,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiMethodNotSupportedException;

	public abstract void selectRouteDirectAgent(LucentAgent paramLucentAgent,
			boolean paramBoolean, UserToUserInfo paramUserToUserInfo)
			throws TsapiMethodNotSupportedException,
			TsapiInvalidArgumentException;

	public abstract void selectRouteWithDigits(String paramString1,
			String paramString2, boolean paramBoolean,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiMethodNotSupportedException;
}
