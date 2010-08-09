package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6OriginatedEvent extends LucentOriginatedEvent {
	static final int PDU = 119;

	public static LucentOriginatedEvent decode(InputStream in) {
		LucentV6OriginatedEvent _this = new LucentV6OriginatedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public int getPDU() {
		return 119;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentV6OriginatedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(physicalTerminal_asn,
				"physicalTerminal", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV6OriginatedEvent JD-Core Version: 0.5.4
 */