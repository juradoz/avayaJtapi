package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentConsultationCall extends LucentPrivateData {
	public static LucentConsultationCall decode(final InputStream in) {
		final LucentConsultationCall _this = new LucentConsultationCall();
		_this.doDecode(in);

		return _this;
	}

	String destRoute;
	boolean priorityCalling;
	LucentUserToUserInfo userInfo;

	static final int PDU = 2;

	public LucentConsultationCall() {
	}

	public LucentConsultationCall(final String _destRoute,
			final boolean _priorityCalling, final LucentUserToUserInfo _userInfo) {
		destRoute = _destRoute;
		priorityCalling = _priorityCalling;
		userInfo = _userInfo;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		destRoute = ASNIA5String.decode(memberStream);
		priorityCalling = ASNBoolean.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(destRoute, memberStream);
		ASNBoolean.encode(priorityCalling, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 2;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentConsultationCall ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(destRoute, "destRoute", indent));
		lines.addAll(ASNBoolean.print(priorityCalling, "priorityCalling",
				indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}
}
