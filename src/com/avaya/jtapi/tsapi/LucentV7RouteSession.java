package com.avaya.jtapi.tsapi;

public abstract interface LucentV7RouteSession extends LucentRouteSession {
	public abstract short getCSTAErrorCode();

	public abstract void selectRouteWithNetworkRedirection(String paramString,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiMethodNotSupportedException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV7RouteSession JD-Core Version: 0.5.4
 */