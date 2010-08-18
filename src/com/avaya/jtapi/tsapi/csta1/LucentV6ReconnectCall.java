package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6ReconnectCall extends LucentReconnectCall {
	public static final int PDU = 114;

	public static LucentReconnectCall decode(final InputStream in) {
		final LucentV6ReconnectCall _this = new LucentV6ReconnectCall();
		_this.doDecode(in);

		return _this;
	}

	LucentV6ReconnectCall() {
	}

	public LucentV6ReconnectCall(final short _dropResource,
			final LucentUserToUserInfo _userInfo) {
		super(_dropResource, _userInfo);
	}

	@Override
	public int getPDU() {
		return 114;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ReconnectCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentDropResource.print(dropResource, "dropResource",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
