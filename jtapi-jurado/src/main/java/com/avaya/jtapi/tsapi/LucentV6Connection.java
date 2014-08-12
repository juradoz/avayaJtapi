package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.CallCenterTrunk;

public abstract interface LucentV6Connection extends LucentV5Connection {
	public abstract CallCenterTrunk getTrunk();
}