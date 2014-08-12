package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentMonitorConfEvent extends LucentPrivateData {
	int usedFilter;
	static final int PDU = 30;

	static LucentMonitorConfEvent decode(InputStream in) {
		LucentMonitorConfEvent _this = new LucentMonitorConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.usedFilter = LucentPrivateFilter.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMonitorConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentPrivateFilter.print(this.usedFilter, "usedFilter",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 30;
	}
}