package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public abstract class ACSUnsolicited extends TsapiPDU {
	public int getPDUClass() {
		return 1;
	}
}