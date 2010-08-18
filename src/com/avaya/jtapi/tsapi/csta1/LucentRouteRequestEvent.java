package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentRouteRequestEvent extends LucentPrivateData {
	String trunkGroup;
	LucentLookaheadInfo lookaheadInfo;
	LucentUserEnteredCode userEnteredCode;
	LucentUserToUserInfo userInfo;
	static final int PDU = 42;

	public static LucentRouteRequestEvent decode(final InputStream in) {
		final LucentRouteRequestEvent _this = new LucentRouteRequestEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		trunkGroup = ASNIA5String.decode(memberStream);
		lookaheadInfo = decodeLookahead(memberStream);
		userEnteredCode = LucentUserEnteredCode.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNIA5String.encode(trunkGroup, memberStream);
		encodeLookahead(lookaheadInfo, memberStream);
		LucentUserEnteredCode.encode(userEnteredCode, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	public LucentLookaheadInfo getLookaheadInfo() {
		return lookaheadInfo;
	}

	@Override
	public int getPDU() {
		return 42;
	}

	public String getTrunkGroup() {
		return trunkGroup;
	}

	public LucentUserEnteredCode getUserEnteredCode() {
		return userEnteredCode;
	}

	public LucentUserToUserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentRouteRequestEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(trunkGroup, "trunkGroup", indent));
		lines.addAll(LucentLookaheadInfo.print(lookaheadInfo, "lookaheadInfo",
				indent));
		lines.addAll(LucentUserEnteredCode.print(userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));

		lines.add("}");
		return lines;
	}

	public void setLookaheadInfo(final LucentLookaheadInfo lookaheadInfo) {
		this.lookaheadInfo = lookaheadInfo;
	}

	public void setTrunkGroup(final String trunkGroup) {
		this.trunkGroup = trunkGroup;
	}

	public void setUserEnteredCode(final LucentUserEnteredCode userEnteredCode) {
		this.userEnteredCode = userEnteredCode;
	}

	public void setUserInfo(final LucentUserToUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
