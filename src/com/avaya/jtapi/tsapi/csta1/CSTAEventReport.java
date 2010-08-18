package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public abstract class CSTAEventReport extends TsapiPDU {
	@Override
	public int getPDUClass() {
		return 6;
	}
}
