package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentConferencedEvent extends LucentPrivateData {
	LucentOriginalCallInfo originalCallInfo;
	CSTAExtendedDeviceID distributingDevice_asn;
	static final int PDU = 59;

	static LucentConferencedEvent decode(InputStream in) {
		LucentConferencedEvent _this = new LucentConferencedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		encodeOCI(this.originalCallInfo, memberStream);
		CSTAExtendedDeviceID.encode(this.distributingDevice_asn, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.originalCallInfo = decodeOCI(memberStream);
		this.distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentConferencedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn,
				"distributingDevice", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 59;
	}

	public CSTAExtendedDeviceID getDistributingDevice_asn() {
		return this.distributingDevice_asn;
	}

	public void setDistributingDevice_asn(
			CSTAExtendedDeviceID _distributingDevice_asn) {
		this.distributingDevice_asn = _distributingDevice_asn;
	}

	public LucentOriginalCallInfo getOriginalCallInfo() {
		return this.originalCallInfo;
	}

	public void setOriginalCallInfo(LucentOriginalCallInfo _info) {
		this.originalCallInfo = _info;
	}
}