package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentMakeCall extends LucentPrivateData {
	public static LucentMakeCall decode(InputStream in) {
		LucentMakeCall _this = new LucentMakeCall();
		_this.doDecode(in);

		return _this;
	}

	String destRoute;
	boolean priorityCalling;
	LucentUserToUserInfo userInfo;

	static final int PDU = 3;

	public LucentMakeCall() {
	}

	public LucentMakeCall(String _destRoute, boolean _priorityCalling,
			LucentUserToUserInfo _userInfo) {
		destRoute = _destRoute;
		priorityCalling = _priorityCalling;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		destRoute = ASNIA5String.decode(memberStream);
		priorityCalling = ASNBoolean.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(destRoute, memberStream);
		ASNBoolean.encode(priorityCalling, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 3;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentMakeCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(destRoute, "destRoute", indent));
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
 * com.avaya.jtapi.tsapi.csta1.LucentMakeCall JD-Core Version: 0.5.4
 */