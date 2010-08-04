package com.avaya.jtapi.tsapi.tsapiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

public abstract interface TsapiChannel
{
  public abstract void write(ByteArrayOutputStream paramByteArrayOutputStream)
    throws IOException;

  public abstract void setReadHandler(TsapiChannelReadHandler paramTsapiChannelReadHandler);

  public abstract InetSocketAddress getInetSocketAddress();

  public abstract void close();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiChannel
 * JD-Core Version:    0.5.4
 */