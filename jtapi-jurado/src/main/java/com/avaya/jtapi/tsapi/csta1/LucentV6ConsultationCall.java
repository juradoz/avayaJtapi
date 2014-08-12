package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV6ConsultationCall extends LucentConsultationCall {
	public static final int PDU = 109;

	public LucentV6ConsultationCall() {
	}

	public LucentV6ConsultationCall(String _destRoute,
			boolean _priorityCalling, LucentUserToUserInfo _userInfo) {
		super(_destRoute, _priorityCalling, _userInfo);
	}

	public static LucentConsultationCall decode(InputStream in) {
		LucentV6ConsultationCall _this = new LucentV6ConsultationCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6ConsultationCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
		lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling",
				indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 109;
	}
}