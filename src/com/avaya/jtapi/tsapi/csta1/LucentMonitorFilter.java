package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentMonitorFilter extends LucentPrivateData {
	int privateFilter;
	static final int PDU = 29;

	LucentMonitorFilter(int _privateFilter) {
		privateFilter = _privateFilter;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		LucentPrivateFilter.encode(privateFilter, memberStream);
	}

	@Override
	public int getPDU() {
		return 29;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMonitorFilter ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentPrivateFilter.print(privateFilter, "privateFilter",
				indent));

		lines.add("}");
		return lines;
	}
}

