package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.LucentConsultOptions;
import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV10ConsultationCall extends LucentV6ConsultationCall {
	short consultOptions;
	public static final int PDU = 149;

	public LucentV10ConsultationCall() {
	}

	public LucentV10ConsultationCall(String _destRoute,
			boolean _priorityCalling, LucentUserToUserInfo _userInfo,
			short consultOptions) {
		super(_destRoute, _priorityCalling, _userInfo);
		this.consultOptions = consultOptions;
	}

	public static LucentConsultationCall decode(InputStream in) {
		LucentV10ConsultationCall _this = new LucentV10ConsultationCall();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.consultOptions = LucentConsultOptions.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentConsultOptions.encode(this.consultOptions, memberStream);
	}

	public int getPDU() {
		return 149;
	}

	public short getConsultOptions() {
		return this.consultOptions;
	}

	public void setConsultOptions(short consultOptions) {
		this.consultOptions = consultOptions;
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV10ConsultationCall ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
		lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling",
				indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));
		lines.addAll(LucentConsultOptions.print(this.consultOptions,
				"consultOptions", indent));

		lines.add("}");
		return lines;
	}
}