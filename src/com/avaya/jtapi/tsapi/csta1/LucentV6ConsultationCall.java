package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6ConsultationCall extends LucentConsultationCall {
	public static final int PDU = 109;

	public static LucentConsultationCall decode(final InputStream in) {
		final LucentV6ConsultationCall _this = new LucentV6ConsultationCall();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6ConsultationCall() {
	}

	public LucentV6ConsultationCall(final String _destRoute,
			final boolean _priorityCalling, final LucentUserToUserInfo _userInfo) {
		super(_destRoute, _priorityCalling, _userInfo);
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
		return 109;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ConsultationCall ::=");
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
