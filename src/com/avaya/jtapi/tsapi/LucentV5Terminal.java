package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.Agent;

public abstract interface LucentV5Terminal extends LucentTerminal {
	public abstract Agent addAgent(LucentAddress paramLucentAddress,
			ACDAddress paramACDAddress, int paramInt1, int paramInt2,
			int paramInt3, String paramString1, String paramString2)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}