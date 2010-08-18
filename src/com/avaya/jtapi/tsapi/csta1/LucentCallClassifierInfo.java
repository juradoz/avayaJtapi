package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentCallClassifierInfo extends LucentPrivateData {
	public int numAvailPorts;
	public int numInUsePorts;
	static final int PDU = 19;

	static LucentCallClassifierInfo decode(final InputStream in) {
		final LucentCallClassifierInfo _this = new LucentCallClassifierInfo();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		numAvailPorts = ASNInteger.decode(memberStream);
		numInUsePorts = ASNInteger.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 19;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CallClassifierInfo ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(numAvailPorts, "numAvailPorts", indent));
		lines.addAll(ASNInteger.print(numInUsePorts, "numInUsePorts", indent));

		lines.add("}");
		return lines;
	}
}
