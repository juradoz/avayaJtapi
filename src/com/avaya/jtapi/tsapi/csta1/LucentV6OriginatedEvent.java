package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6OriginatedEvent extends LucentOriginatedEvent {
	static final int PDU = 119;

	public static LucentOriginatedEvent decode(final InputStream in) {
		final LucentV6OriginatedEvent _this = new LucentV6OriginatedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public int getPDU() {
		return 119;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6OriginatedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(physicalTerminal_asn,
				"physicalTerminal", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
