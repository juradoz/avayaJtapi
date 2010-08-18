package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentReconnectCall extends LucentPrivateData {
	public static LucentReconnectCall decode(final InputStream in) {
		final LucentReconnectCall _this = new LucentReconnectCall();
		_this.doDecode(in);

		return _this;
	}

	short dropResource;
	LucentUserToUserInfo userInfo;

	static final int PDU = 7;

	LucentReconnectCall() {
	}

	LucentReconnectCall(final short _dropResource,
			final LucentUserToUserInfo _userInfo) {
		dropResource = _dropResource;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		dropResource = ASNEnumerated.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(dropResource, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 7;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentReconnectCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentDropResource.print(dropResource, "dropResource",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
