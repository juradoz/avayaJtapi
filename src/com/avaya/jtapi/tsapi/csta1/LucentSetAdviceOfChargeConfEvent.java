package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNNull;

public final class LucentSetAdviceOfChargeConfEvent extends LucentPrivateData {
	static final int PDU = 100;

	static LucentSetAdviceOfChargeConfEvent decode(InputStream in) {
		LucentSetAdviceOfChargeConfEvent _this = new LucentSetAdviceOfChargeConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 100;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSetAdviceOfChargeConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}
}
