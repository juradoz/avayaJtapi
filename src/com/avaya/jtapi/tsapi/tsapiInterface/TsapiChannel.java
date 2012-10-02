package com.avaya.jtapi.tsapi.tsapiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

public abstract interface TsapiChannel {
	public abstract void write(ByteArrayOutputStream paramByteArrayOutputStream)
			throws IOException;

	public abstract void setReadHandler(
			TsapiChannelReadHandler paramTsapiChannelReadHandler);

	public abstract InetSocketAddress getInetSocketAddress();

	public abstract void close();
}