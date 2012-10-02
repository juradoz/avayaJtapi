package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNNull;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentSetBillRateConfEvent extends LucentPrivateData {
	static final int PDU = 75;

	static LucentSetBillRateConfEvent decode(InputStream in) {
		LucentSetBillRateConfEvent _this = new LucentSetBillRateConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSetBillRateConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 75;
	}
}