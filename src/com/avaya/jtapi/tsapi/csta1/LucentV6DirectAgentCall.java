package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6DirectAgentCall extends LucentDirectAgentCall {
	public static final int PDU = 111;

	public static LucentDirectAgentCall decode(InputStream in) {
		LucentV6DirectAgentCall _this = new LucentV6DirectAgentCall();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6DirectAgentCall() {
	}

	public LucentV6DirectAgentCall(String _split, boolean _priorityCalling,
			LucentUserToUserInfo _userInfo) {
		super(_split, _priorityCalling, _userInfo);
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
		return 111;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6DirectAgentCall ::=");
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

