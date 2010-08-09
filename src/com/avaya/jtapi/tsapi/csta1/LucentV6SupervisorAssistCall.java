package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6SupervisorAssistCall extends
		LucentSupervisorAssistCall {
	public static final int PDU = 113;

	public static LucentSupervisorAssistCall decode(InputStream in) {
		LucentV6SupervisorAssistCall _this = new LucentV6SupervisorAssistCall();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6SupervisorAssistCall() {
	}

	public LucentV6SupervisorAssistCall(String _split,
			LucentUserToUserInfo _userInfo) {
		super(_split, _userInfo);
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	@Override
	public int getPDU() {
		return 113;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("LucentV6SupervisorAssistCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(split, "split", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV6SupervisorAssistCall JD-Core Version:
 * 0.5.4
 */