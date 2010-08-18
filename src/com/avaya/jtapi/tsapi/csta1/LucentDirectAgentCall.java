package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentDirectAgentCall extends LucentPrivateData {
	public static LucentDirectAgentCall decode(InputStream in) {
		LucentDirectAgentCall _this = new LucentDirectAgentCall();
		_this.doDecode(in);

		return _this;
	}

	String split;
	boolean priorityCalling;
	LucentUserToUserInfo userInfo;

	static final int PDU = 4;

	public LucentDirectAgentCall() {
	}

	public LucentDirectAgentCall(String _split, boolean _priorityCalling,
			LucentUserToUserInfo _userInfo) {
		split = _split;
		priorityCalling = _priorityCalling;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		split = ASNIA5String.decode(memberStream);
		priorityCalling = ASNBoolean.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(split, memberStream);
		ASNBoolean.encode(priorityCalling, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 4;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentDirectAgentCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(split, "split", indent));
		lines.addAll(ASNBoolean.print(priorityCalling, "priorityCalling",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentDirectAgentCall JD-Core Version: 0.5.4
 */