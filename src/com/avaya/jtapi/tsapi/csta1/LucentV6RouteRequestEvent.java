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

	public static LucentRouteRequestEvent decode(final InputStream in) {
		final LucentV6RouteRequestEvent _this = new LucentV6RouteRequestEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		trunkMember = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6RouteRequestEvent ::=");
		lines.add("{");

		final String indent = "  ";

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

	public void setTrunkMember(final String trunkMember) {
		this.trunkMember = trunkMember;
	}
}
