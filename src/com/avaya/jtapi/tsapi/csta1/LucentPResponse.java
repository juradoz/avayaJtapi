package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;

import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

class LucentPResponse extends LucentPrivateData {
	byte[] value;
	static final int PDU = 122;

	LucentPResponse(byte[] _val) {
		value = _val;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNOctetString.encode(value, memberStream);
	}

	@Override
	public int getPDU() {
		return 122;
	}

	@Override
	public Collection<String> print() {
		return Collections.emptyList();
	}

}

