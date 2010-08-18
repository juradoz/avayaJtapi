package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6ClearConnection extends LucentClearConnection {
	static final int PDU = 108;

	public static LucentClearConnection decode(InputStream in) {
		LucentV6ClearConnection _this = new LucentV6ClearConnection();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6ClearConnection() {
	}

	public LucentV6ClearConnection(short _dropResource,
			LucentUserToUserInfo _userInfo) {
		super(_dropResource, _userInfo);
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
		return 108;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ClearConnection ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentDropResource.print(dropResource, "dropResource",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV6ClearConnection JD-Core Version: 0.5.4
 */