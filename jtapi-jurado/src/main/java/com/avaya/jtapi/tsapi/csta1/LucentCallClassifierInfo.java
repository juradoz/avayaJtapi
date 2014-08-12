package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentCallClassifierInfo extends LucentPrivateData {
	public int numAvailPorts;
	public int numInUsePorts;
	static final int PDU = 19;

	static LucentCallClassifierInfo decode(InputStream in) {
		LucentCallClassifierInfo _this = new LucentCallClassifierInfo();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.numAvailPorts = ASNInteger.decode(memberStream);
		this.numInUsePorts = ASNInteger.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CallClassifierInfo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(this.numAvailPorts, "numAvailPorts",
				indent));
		lines.addAll(ASNInteger.print(this.numInUsePorts, "numInUsePorts",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 19;
	}
}