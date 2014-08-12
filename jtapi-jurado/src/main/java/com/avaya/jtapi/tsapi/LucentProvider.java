package com.avaya.jtapi.tsapi;

import java.util.Date;

public abstract interface LucentProvider extends ITsapiProviderEx2 {
	public abstract TrunkGroupInfo getTrunkGroupInfo(String paramString)
			throws TsapiMethodNotSupportedException;

	public abstract CallClassifierInfo getCallClassifierInfo()
			throws TsapiMethodNotSupportedException;

	public abstract Date getSwitchDateAndTime()
			throws TsapiMethodNotSupportedException;
}