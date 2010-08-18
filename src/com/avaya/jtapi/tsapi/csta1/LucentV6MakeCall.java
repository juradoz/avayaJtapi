package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6MakeCall extends LucentMakeCall {
	public static final int PDU = 110;

	public static LucentMakeCall decode(final InputStream in) {
		final LucentV6MakeCall _this = new LucentV6MakeCall();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6MakeCall() {
	}

	public LucentV6MakeCall(final String _destRoute,
			final boolean _priorityCalling, final LucentUserToUserInfo _userInfo) {
		super(_destRoute, _priorityCalling, _userInfo);
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	@Override
	public int getPDU() {
		return 110;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6MakeCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(destRoute, "destRoute", indent));
		lines.addAll(ASNBoolean.print(priorityCalling, "priorityCalling",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
