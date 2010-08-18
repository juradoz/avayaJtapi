package com.avaya.jtapi.tsapi;

import javax.telephony.callcenter.Agent;

public abstract interface LucentV5TerminalEx extends LucentV5Terminal {
	public abstract void removeAgent(Agent paramAgent, int paramInt)
			throws TsapiInvalidArgumentException, TsapiInvalidStateException;
}

