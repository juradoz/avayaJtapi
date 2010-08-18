package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentV6RouteRequestEvent extends LucentV5RouteRequestEvent {
	String trunkMember;
	static final int PDU = 105;

	public static LucentRouteRequestEvent decode(InputStream in) {
		LucentV6RouteRequestEvent _this = new LucentV6RouteRequestEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		trunkMember = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNIA5String.encode(trunkMember, memberStream);
	}

	@Override
	public int getPDU() {
		return 105;
	}

	public String getTrunkMember() {
		return trunkMember;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6RouteRequestEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(trunkGroup, "trunkGroup", indent));
		lines.addAll(LucentLookaheadInfo.print(lookaheadInfo, "lookaheadInfo",
				indent));
		lines.addAll(LucentUserEnteredCode.print(userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));
		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));
		lines.addAll(CSTACallOriginatorInfo.print(callOriginatorInfo,
				"callOriginatorInfo", indent));
		lines.addAll(ASNBoolean.print(flexibleBilling, "flexibleBilling",
				indent));
		lines.addAll(ASNIA5String.print(trunkMember, "trunkMember", indent));

		lines.add("}");
		return lines;
	}

	public void setTrunkMember(String trunkMember) {
		this.trunkMember = trunkMember;
	}
}

