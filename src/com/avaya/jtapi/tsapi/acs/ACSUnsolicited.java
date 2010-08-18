package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public abstract class ACSUnsolicited extends TsapiPDU {
	@Override
	public int getPDUClass() {
		return 1;
	}
}
