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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentRouteSession JD-Core Version: 0.5.4
 */