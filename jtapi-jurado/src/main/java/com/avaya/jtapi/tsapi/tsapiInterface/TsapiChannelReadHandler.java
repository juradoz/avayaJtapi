package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.tsapiInterface.streams.IntelByteArrayInputStream;

public abstract interface TsapiChannelReadHandler {
	public abstract void handleRead(
			IntelByteArrayInputStream paramIntelByteArrayInputStream);

	public abstract void handleException(Exception paramException);
}