package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.TsapiRequest;

public abstract class CSTARequest extends TsapiRequest {
	@Override
	public int getPDUClass() {
		return 3;
	}
}

