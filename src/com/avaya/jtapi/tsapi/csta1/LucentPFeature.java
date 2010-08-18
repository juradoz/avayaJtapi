package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

class LucentPFeature extends LucentPrivateData {
	byte[] value;
	static final int PDU = 120;

	LucentPFeature() {
		value = new byte[32];
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		final Random r = new Random();
		r.nextBytes(value);
		ASNOctetString.encode(value, memberStream);
	}

	@Override
	public int getPDU() {
		return 120;
	}

	@Override
	public Collection<String> print() {
		return Collections.emptyList();
	}

}
