package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentSupervisorAssistCall extends LucentPrivateData {
	public static LucentSupervisorAssistCall decode(InputStream in) {
		LucentSupervisorAssistCall _this = new LucentSupervisorAssistCall();
		_this.doDecode(in);

		return _this;
	}

	String split;
	LucentUserToUserInfo userInfo;

	static final int PDU = 6;

	public LucentSupervisorAssistCall() {
	}

	public LucentSupervisorAssistCall(String _split,
			LucentUserToUserInfo _userInfo) {
		split = _split;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		split = ASNIA5String.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(split, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 6;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSupervisorAssistCall ::=");
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
 * com.avaya.jtapi.tsapi.csta1.LucentSupervisorAssistCall JD-Core Version: 0.5.4
 */