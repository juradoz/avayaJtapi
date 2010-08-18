package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV6ConsultationCall extends LucentConsultationCall {
	public static final int PDU = 109;

	public static LucentConsultationCall decode(InputStream in) {
		LucentV6ConsultationCall _this = new LucentV6ConsultationCall();
		_this.doDecode(in);

		return _this;
	}

	public LucentV6ConsultationCall() {
	}

	public LucentV6ConsultationCall(String _destRoute,
			boolean _priorityCalling, LucentUserToUserInfo _userInfo) {
		super(_destRoute, _priorityCalling, _userInfo);
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
		return 109;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ConsultationCall ::=");
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
 * com.avaya.jtapi.tsapi.csta1.LucentV6ConsultationCall JD-Core Version: 0.5.4
 */