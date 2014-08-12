package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentOriginatedEvent extends LucentPrivateData {
	String physicalTerminal_asn;
	LucentUserToUserInfo userInfo;
	static final int PDU = 47;

	public static LucentOriginatedEvent decode(InputStream in) {
		LucentOriginatedEvent _this = new LucentOriginatedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.physicalTerminal_asn = DeviceID.decode(memberStream);
		this.userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.physicalTerminal_asn, memberStream);
		LucentUserToUserInfo.encode(this.userInfo, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentOriginatedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.physicalTerminal_asn,
				"physicalTerminal", indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 47;
	}

	public String getPhysicalTerminal_asn() {
		return this.physicalTerminal_asn;
	}

	public LucentUserToUserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setPhysicalTerminal_asn(String physicalTerminal_asn) {
		this.physicalTerminal_asn = physicalTerminal_asn;
	}

	public void setUserInfo(LucentUserToUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}