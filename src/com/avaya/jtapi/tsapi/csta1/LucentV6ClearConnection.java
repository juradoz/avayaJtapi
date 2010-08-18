package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6ClearConnection extends LucentClearConnection {
	static final int PDU = 108;

	public static LucentClearConnection decode(final InputStream in) {
		final LucentV6ClearConnection _this = new LucentV6ClearConnection();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6ClearConnection() {
	}

	public LucentV6ClearConnection(final short _dropResource,
			final LucentUserToUserInfo _userInfo) {
		super(_dropResource, _userInfo);
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
		return 108;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ClearConnection ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentDropResource.print(dropResource, "dropResource",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
