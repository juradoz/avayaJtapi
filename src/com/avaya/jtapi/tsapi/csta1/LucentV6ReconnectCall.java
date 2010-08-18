package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6ReconnectCall extends LucentReconnectCall {
	public static final int PDU = 114;

	public static LucentReconnectCall decode(InputStream in) {
		LucentV6ReconnectCall _this = new LucentV6ReconnectCall();
		_this.doDecode(in);

		return _this;
	}

	LucentV6ReconnectCall() {
	}

	public LucentV6ReconnectCall(short _dropResource,
			LucentUserToUserInfo _userInfo) {
		super(_dropResource, _userInfo);
	}

	@Override
	public int getPDU() {
		return 114;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ReconnectCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentDropResource.print(dropResource, "dropResource",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}

