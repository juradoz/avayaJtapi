package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentCallClassifierInfo extends LucentPrivateData {
	public int numAvailPorts;
	public int numInUsePorts;
	static final int PDU = 19;

	static LucentCallClassifierInfo decode(InputStream in) {
		LucentCallClassifierInfo _this = new LucentCallClassifierInfo();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		numAvailPorts = ASNInteger.decode(memberStream);
		numInUsePorts = ASNInteger.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 19;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("CallClassifierInfo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(numAvailPorts, "numAvailPorts", indent));
		lines.addAll(ASNInteger.print(numInUsePorts, "numInUsePorts", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentCallClassifierInfo JD-Core Version: 0.5.4
 */