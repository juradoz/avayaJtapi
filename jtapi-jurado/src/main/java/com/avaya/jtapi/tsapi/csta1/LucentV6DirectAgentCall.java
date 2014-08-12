package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6DirectAgentCall extends LucentDirectAgentCall {
	public static final int PDU = 111;

	public LucentV6DirectAgentCall() {
	}

	public LucentV6DirectAgentCall(String _split, boolean _priorityCalling,
			LucentUserToUserInfo _userInfo) {
		super(_split, _priorityCalling, _userInfo);
	}

	public static LucentDirectAgentCall decode(InputStream in) {
		LucentV6DirectAgentCall _this = new LucentV6DirectAgentCall();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6DirectAgentCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.split, "split", indent));
		lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling",
				indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 111;
	}
}