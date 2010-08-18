package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

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

	@Override
	public LucentLookaheadInfo decodeLookahead(InputStream memberStream) {
		return LucentV5LookaheadInfo.decode(memberStream);
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		ucid = ASNIA5String.decode(memberStream);
		callOriginatorInfo = CSTACallOriginatorInfo.decode(memberStream);
		flexibleBilling = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNIA5String.encode(ucid, memberStream);
		ASNSequence.encode(callOriginatorInfo, memberStream);
		ASNBoolean.encode(flexibleBilling, memberStream);
	}

	public CSTACallOriginatorInfo getCallOriginatorInfo() {
		return callOriginatorInfo;
	}

	@Override
	public int getPDU() {
		return 83;
	}

	public String getUcid() {
		return ucid;
	}

	public boolean isFlexibleBilling() {
		return flexibleBilling;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5RouteRequestEvent ::=");
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

		lines.add("}");
		return lines;
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

