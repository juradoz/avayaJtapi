package com.avaya.jtapi.tsapi;

public abstract interface LucentV7Provider extends LucentV5Provider {
	public abstract String getAdministeredSwitchSoftwareVersion();

	public abstract String getSwitchSoftwareVersion();

	public abstract String getOfferType();

	public abstract String getServerType();
}