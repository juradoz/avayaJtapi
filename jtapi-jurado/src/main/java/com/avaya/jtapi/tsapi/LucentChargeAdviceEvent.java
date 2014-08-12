package com.avaya.jtapi.tsapi;

public abstract interface LucentChargeAdviceEvent {
	public abstract LucentCall getCall();

	public abstract LucentAddress getCalledAddress();

	public abstract LucentAddress getChargingAddress();

	public abstract TsapiTrunk getTrunk();

	public abstract int getCharge();

	public abstract short getChargeType();

	public abstract short getChargeError();
}