package com.avaya.jtapi.tsapi;

public abstract interface LucentV7RouteSession extends LucentRouteSession {
	public abstract short getCSTAErrorCode();

	public abstract void selectRouteWithNetworkRedirection(String paramString,
			UserToUserInfo paramUserToUserInfo)
			throws TsapiMethodNotSupportedException;
}

