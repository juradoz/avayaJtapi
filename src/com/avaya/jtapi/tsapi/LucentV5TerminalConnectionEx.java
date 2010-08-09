package com.avaya.jtapi.tsapi;

public abstract interface LucentV5TerminalConnectionEx extends
		LucentV5TerminalConnection {
	public abstract void generateDtmf(String paramString, int paramInt1,
			int paramInt2) throws TsapiMethodNotSupportedException,
			TsapiResourceUnavailableException, TsapiInvalidStateException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentV5TerminalConnectionEx JD-Core Version: 0.5.4
 */