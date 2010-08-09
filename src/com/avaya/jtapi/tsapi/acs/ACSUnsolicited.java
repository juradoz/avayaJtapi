package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.TsapiPDU;

public abstract class ACSUnsolicited extends TsapiPDU {
	@Override
	public int getPDUClass() {
		return 1;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.acs.ACSUnsolicited JD-Core Version: 0.5.4
 */