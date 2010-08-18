package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentOriginatedEvent extends LucentPrivateData {
	String physicalTerminal_asn;
	LucentUserToUserInfo userInfo;
	static final int PDU = 47;

	public static LucentOriginatedEvent decode(InputStream in) {
		LucentOriginatedEvent _this = new LucentOriginatedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		physicalTerminal_asn = ASNIA5String.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(physicalTerminal_asn, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	@Override
	public int getPDU() {
		return 47;
	}

	public String getPhysicalTerminal_asn() {
		return physicalTerminal_asn;
	}

	public LucentUserToUserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentOriginatedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(physicalTerminal_asn,
				"physicalTerminal", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}

	public void setPhysicalTerminal_asn(String physicalTerminal_asn) {
		this.physicalTerminal_asn = physicalTerminal_asn;
	}

	public void setUserInfo(LucentUserToUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}

