package com.avaya.jtapi.tsapi;

public abstract interface LucentV5TerminalConnectionEx extends
		LucentV5TerminalConnection {
	public abstract void generateDtmf(String paramString, int paramInt1,
			int paramInt2) throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiInvalidStateException;
}

