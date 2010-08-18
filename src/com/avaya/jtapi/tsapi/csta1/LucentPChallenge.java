package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import com.avaya.jtapi.tsapi.asn1.ASNOctetString;

class LucentPChallenge extends LucentPrivateData {
	byte[] value;
	static final int PDU = 121;

	static LucentPChallenge decode(InputStream in) {
		LucentPChallenge _this = new LucentPChallenge();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		value = ASNOctetString.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 121;
	}

	@Override
	public Collection<String> print() {
		return Collections.emptyList();
	}

}

