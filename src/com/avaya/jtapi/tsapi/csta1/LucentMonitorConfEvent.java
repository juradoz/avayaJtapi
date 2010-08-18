package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public class LucentMonitorConfEvent extends LucentPrivateData {
	int usedFilter;
	static final int PDU = 30;

	static LucentMonitorConfEvent decode(final InputStream in) {
		final LucentMonitorConfEvent _this = new LucentMonitorConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		usedFilter = ASNBitString.decode(memberStream);
	}

	@Override
	public int getPDU() {
		return 30;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMonitorConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentPrivateFilter
				.print(usedFilter, "usedFilter", indent));

		lines.add("}");
		return lines;
	}
}
