package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV5RouteRequestEvent extends LucentRouteRequestEvent
		implements HasUCID {
	String ucid;
	CSTACallOriginatorInfo callOriginatorInfo;
	boolean flexibleBilling;
	public static final int PDU = 83;

	public static LucentRouteRequestEvent decode(InputStream in) {
		LucentV5RouteRequestEvent _this = new LucentV5RouteRequestEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.ucid = UCID.decode(memberStream);
		this.callOriginatorInfo = CSTACallOriginatorInfo.decode(memberStream);
		this.flexibleBilling = ASNBoolean.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		UCID.encode(this.ucid, memberStream);
		CSTACallOriginatorInfo.encode(this.callOriginatorInfo, memberStream);
		ASNBoolean.encode(this.flexibleBilling, memberStream);
	}

	public LucentLookaheadInfo decodeLookahead(InputStream memberStream) {
		return LucentV5LookaheadInfo.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5RouteRequestEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
		lines.addAll(LucentV5LookaheadInfo.print(
				(LucentV5LookaheadInfo) this.lookaheadInfo, "lookaheadInfo",
				indent));
		lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));
		lines.addAll(UCID.print(this.ucid, "ucid", indent));
		lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo,
				"callOriginatorInfo", indent));
		lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 83;
	}

	public CSTACallOriginatorInfo getCallOriginatorInfo() {
		return this.callOriginatorInfo;
	}

	public boolean isFlexibleBilling() {
		return this.flexibleBilling;
	}

	public String getUcid() {
		return this.ucid;
	}

	public void setCallOriginatorInfo(CSTACallOriginatorInfo callOriginatorInfo) {
		this.callOriginatorInfo = callOriginatorInfo;
	}

	public void setFlexibleBilling(boolean flexibleBilling) {
		this.flexibleBilling = flexibleBilling;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
}