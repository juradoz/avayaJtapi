package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentSupervisorAssistCall extends LucentPrivateData {
	public static LucentSupervisorAssistCall decode(final InputStream in) {
		final LucentSupervisorAssistCall _this = new LucentSupervisorAssistCall();
		_this.doDecode(in);

		return _this;
	}

	String split;
	LucentUserToUserInfo userInfo;

	static final int PDU = 6;

	public LucentSupervisorAssistCall() {
	}

	public LucentSupervisorAssistCall(final String _split,
			final LucentUserToUserInfo _userInfo) {
		split = _split;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		split = ASNIA5String.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(split, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 6;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSupervisorAssistCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(split, "split", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
