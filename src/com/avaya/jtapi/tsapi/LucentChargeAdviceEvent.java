package com.avaya.jtapi.tsapi;

public abstract interface LucentChargeAdviceEvent {
	public abstract LucentCall getCall();

	public abstract LucentAddress getCalledAddress();

	public abstract int getCharge();

	public abstract short getChargeError();

	public abstract short getChargeType();

	public abstract LucentAddress getChargingAddress();

	public abstract TsapiTrunk getTrunk();
}
