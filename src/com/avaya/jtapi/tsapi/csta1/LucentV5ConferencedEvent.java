package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV5ConferencedEvent extends LucentConferencedEvent implements
		HasUCID {
	String ucid;
	static final int PDU = 78;

	public static LucentConferencedEvent decode(InputStream in) {
		LucentV5ConferencedEvent _this = new LucentV5ConferencedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		UCID.encode(this.ucid, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.ucid = UCID.decode(memberStream);
	}

	public void encodeOCI(LucentOriginalCallInfo callInfo,
			OutputStream memberStream) {
		LucentV5OriginalCallInfo.encode(callInfo, memberStream);
	}

	public LucentOriginalCallInfo decodeOCI(InputStream memberStream) {
		return LucentV5OriginalCallInfo.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5ConferencedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentV5OriginalCallInfo.print(
				(LucentV5OriginalCallInfo) this.originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn,
				"distributingDevice", indent));
		lines.addAll(UCID.print(this.ucid, "ucid", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 78;
	}

	public String getUcid() {
		return this.ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
}