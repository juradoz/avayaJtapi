package com.avaya.jtapi.tsapi;

public abstract interface LucentV7Provider extends LucentV5Provider {
	public abstract String getAdministeredSwitchSoftwareVersion();

	public abstract String getOfferType();

	public abstract String getServerType();

	public abstract String getSwitchSoftwareVersion();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV7Provider JD-Core Version: 0.5.4
 */