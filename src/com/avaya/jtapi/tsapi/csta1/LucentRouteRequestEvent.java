package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentRouteRequestEvent extends LucentPrivateData {
	String trunkGroup;
	LucentLookaheadInfo lookaheadInfo;
	LucentUserEnteredCode userEnteredCode;
	LucentUserToUserInfo userInfo;
	static final int PDU = 42;

	public static LucentRouteRequestEvent decode(InputStream in) {
		LucentRouteRequestEvent _this = new LucentRouteRequestEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.trunkGroup = DeviceID.decode(memberStream);
		this.lookaheadInfo = decodeLookahead(memberStream);
		this.userEnteredCode = LucentUserEnteredCode.decode(memberStream);
		this.userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		DeviceID.encode(this.trunkGroup, memberStream);
		encodeLookahead(this.lookaheadInfo, memberStream);
		LucentUserEnteredCode.encode(this.userEnteredCode, memberStream);
		LucentUserToUserInfo.encode(this.userInfo, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentRouteRequestEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
		lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo,
				"lookaheadInfo", indent));
		lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 42;
	}

	public LucentLookaheadInfo getLookaheadInfo() {
		return this.lookaheadInfo;
	}

	public String getTrunkGroup() {
		return this.trunkGroup;
	}

	public LucentUserEnteredCode getUserEnteredCode() {
		return this.userEnteredCode;
	}

	public LucentUserToUserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setLookaheadInfo(LucentLookaheadInfo lookaheadInfo) {
		this.lookaheadInfo = lookaheadInfo;
	}

	public void setTrunkGroup(String trunkGroup) {
		this.trunkGroup = trunkGroup;
	}

	public void setUserEnteredCode(LucentUserEnteredCode userEnteredCode) {
		this.userEnteredCode = userEnteredCode;
	}

	public void setUserInfo(LucentUserToUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}