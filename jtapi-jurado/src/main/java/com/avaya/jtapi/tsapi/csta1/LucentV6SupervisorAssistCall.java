package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6SupervisorAssistCall extends
		LucentSupervisorAssistCall {
	public static final int PDU = 113;

	public LucentV6SupervisorAssistCall() {
	}

	public LucentV6SupervisorAssistCall(String _split,
			LucentUserToUserInfo _userInfo) {
		super(_split, _userInfo);
	}

	public static LucentSupervisorAssistCall decode(InputStream in) {
		LucentV6SupervisorAssistCall _this = new LucentV6SupervisorAssistCall();
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
		lines.add("LucentV6SupervisorAssistCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.split, "split", indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 113;
	}
}