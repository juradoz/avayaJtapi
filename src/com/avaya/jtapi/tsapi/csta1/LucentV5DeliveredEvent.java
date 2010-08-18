package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class LucentV5DeliveredEvent extends LucentDeliveredEvent implements
		HasUCID {
	String ucid;
	CSTACallOriginatorInfo callOriginatorInfo;
	boolean flexibleBilling;
	static final int PDU = 80;

	public static LucentDeliveredEvent decode(final InputStream in) {
		final LucentV5DeliveredEvent _this = new LucentV5DeliveredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public LucentLookaheadInfo decodeLookahead(final InputStream memberStream) {
		return LucentV5LookaheadInfo.decode(memberStream);
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		ucid = ASNIA5String.decode(memberStream);
		callOriginatorInfo = CSTACallOriginatorInfo.decode(memberStream);
		flexibleBilling = ASNBoolean.decode(memberStream);
	}

	@Override
	public LucentOriginalCallInfo decodeOCI(final InputStream memberStream) {
		return LucentV5OriginalCallInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		return 80;
	}

	public String getUcid() {
		return ucid;
	}

	public boolean isFlexibleBilling() {
		return flexibleBilling;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5DeliveredEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentDeliveredType.print(deliveredType, "deliveredType",
				indent));
		lines.addAll(ASNIA5String.print(trunkGroup, "trunkGroup", indent));
		lines.addAll(ASNIA5String.print(trunkMember, "trunkMember", indent));
		lines.addAll(ASNIA5String.print(split_asn, "split", indent));
		lines.addAll(LucentLookaheadInfo.print(lookaheadInfo, "lookaheadInfo",
				indent));
		lines.addAll(LucentUserEnteredCode.print(userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));
		lines.addAll(LucentReasonCode.print(reason, "reason", indent));
		lines.addAll(LucentV5OriginalCallInfo.print(
				(LucentV5OriginalCallInfo) originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));
		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));
		lines.addAll(CSTACallOriginatorInfo.print(callOriginatorInfo,
				"callOriginatorInfo", indent));
		lines.addAll(ASNBoolean.print(flexibleBilling, "flexibleBilling",
				indent));

		lines.add("}");
		return lines;
	}

	public void setCallOriginatorInfo(
			final CSTACallOriginatorInfo callOriginatorInfo) {
		this.callOriginatorInfo = callOriginatorInfo;
	}

	public void setFlexibleBilling(final boolean flexibleBilling) {
		this.flexibleBilling = flexibleBilling;
	}

	public void setUcid(final String ucid) {
		this.ucid = ucid;
	}
}
