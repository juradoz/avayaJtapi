package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6SupervisorAssistCall extends
		LucentSupervisorAssistCall {
	public static final int PDU = 113;

	public static LucentSupervisorAssistCall decode(final InputStream in) {
		final LucentV6SupervisorAssistCall _this = new LucentV6SupervisorAssistCall();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6SupervisorAssistCall() {
	}

	public LucentV6SupervisorAssistCall(final String _split,
			final LucentUserToUserInfo _userInfo) {
		super(_split, _userInfo);
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
		return 113;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("LucentV6SupervisorAssistCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(split, "split", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
