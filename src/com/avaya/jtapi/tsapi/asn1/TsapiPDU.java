package com.avaya.jtapi.tsapi.asn1;

public abstract class TsapiPDU extends ASNSequence
{
  public abstract int getPDU();

  public abstract int getPDUClass();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.TsapiPDU
 * JD-Core Version:    0.5.4
 */