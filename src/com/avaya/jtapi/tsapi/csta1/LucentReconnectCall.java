package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentReconnectCall extends LucentPrivateData {
	public static LucentReconnectCall decode(InputStream in) {
		LucentReconnectCall _this = new LucentReconnectCall();
		_this.doDecode(in);

		return _this;
	}

	short dropResource;
	LucentUserToUserInfo userInfo;

	static final int PDU = 7;

	LucentReconnectCall() {
	}

	LucentReconnectCall(short _dropResource, LucentUserToUserInfo _userInfo) {
		dropResource = _dropResource;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		dropResource = ASNEnumerated.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(dropResource, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 7;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentReconnectCall ::=");
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
 * com.avaya.jtapi.tsapi.csta1.LucentReconnectCall JD-Core Version: 0.5.4
 */