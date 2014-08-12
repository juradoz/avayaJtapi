package com.avaya.jtapi.tsapi;

public abstract interface LucentConnNetworkReachedEvent extends
		ITsapiConnNetworkReachedEvent {
	public abstract NetworkProgressInfo getNetworkProgressInfo();
}