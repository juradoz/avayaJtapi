package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayInputStream;

public abstract interface TsapiChannelReadHandler {
	public abstract void handleException(Exception paramException);

	public abstract void handleRead(
			IntelByteArrayInputStream paramIntelByteArrayInputStream);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannelReadHandler JD-Core Version:
 * 0.5.4
 */