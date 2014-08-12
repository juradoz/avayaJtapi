package com.avaya.jtapi.tsapi.asn1;

public abstract class TsapiPDU extends ASNSequence {
	public abstract int getPDU();

	public abstract int getPDUClass();
}