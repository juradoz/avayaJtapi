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

	public static LucentOriginatedEvent decode(final InputStream in) {
		final LucentOriginatedEvent _this = new LucentOriginatedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		physicalTerminal_asn = ASNIA5String.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentOriginatedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(physicalTerminal_asn,
				"physicalTerminal", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}

	public void setPhysicalTerminal_asn(final String physicalTerminal_asn) {
		this.physicalTerminal_asn = physicalTerminal_asn;
	}

	public void setUserInfo(final LucentUserToUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
