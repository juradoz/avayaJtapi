package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class LucentV5OriginalCallInfo extends LucentOriginalCallInfo {
	public static LucentOriginalCallInfo decode(InputStream in) {
		LucentV5OriginalCallInfo _this = new LucentV5OriginalCallInfo();
		_this.doDecode(in);
		if ((_this.callingDevice_asn == null)
				&& (_this.calledDevice_asn == null)
				&& (_this.trunkGroup == null) && (_this.trunkMember == null)
				&& (_this.lookaheadInfo == null)
				&& (_this.userEnteredCode == null) && (_this.userInfo == null)
				&& (_this.ucid == null) && (_this.callOriginatorInfo == null)) {
			return null;
		}
		return _this;
	}

	public static Collection<String> print(LucentV5OriginalCallInfo _this,
			String name, String _indent) {
		Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(ReasonForCallInfo.print(_this.reason, "reason", indent));
		lines.addAll(CSTAExtendedDeviceID.print(_this.callingDevice_asn,
				"callingDevice", indent));
		lines.addAll(CSTAExtendedDeviceID.print(_this.calledDevice_asn,
				"calledDevice", indent));
		lines
				.addAll(ASNIA5String.print(_this.trunkGroup, "trunkGroup",
						indent));
		lines.addAll(ASNIA5String.print(_this.trunkMember, "trunkMember",
				indent));
		lines.addAll(LucentLookaheadInfo.print(_this.lookaheadInfo,
				"lookaheadInfo", indent));
		lines.addAll(LucentUserEnteredCode.print(_this.userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(_this.userInfo, "userInfo",
				indent));
		lines.addAll(ASNIA5String.print(_this.ucid, "ucid", indent));
		lines.addAll(CSTACallOriginatorInfo.print(_this.callOriginatorInfo,
				"callOriginatorInfo", indent));
		lines.addAll(ASNBoolean.print(_this.flexibleBilling, "flexibleBilling",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	String ucid;

	CSTACallOriginatorInfo callOriginatorInfo;

	boolean flexibleBilling;

	public boolean canSetBillRate() {
		return flexibleBilling;
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

	public int getCallOriginatorType() {
		if (hasCallOriginatorType()) {
			return callOriginatorInfo.getCallOriginatorType();
		}
		return -1;
	}

	public String getUCID() {
		return ucid;
	}

	public boolean hasCallOriginatorType() {
		return callOriginatorInfo != null;
	}

	public void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo) {
		callOriginatorInfo = _callOriginatorInfo;
	}

	public void setFlexibleBilling(boolean flexibleBilling) {
		this.flexibleBilling = flexibleBilling;
	}

	public void setUCID(String _ucid) {
		ucid = _ucid;
	}
}

