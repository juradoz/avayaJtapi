package com.avaya.jtapi.tsapi;

public abstract interface LucentV5Provider extends LucentProvider {
	public abstract void setAdviceOfCharge(boolean paramBoolean)
			throws TsapiMethodNotSupportedException;
}

