package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class LucentOriginalCallInfo extends LucentPrivateData {
	public static final short OR_NONE = 0;
	public static final short OR_CONSULTATION = 1;
	public static final short OR_CONFERENCED = 2;
	public static final short OR_TRANSFERRED = 3;
	public static final short OR_NEW_CALL = 4;

	public static LucentOriginalCallInfo decode(InputStream in) {
		LucentOriginalCallInfo _this = new LucentOriginalCallInfo();
		_this.doDecode(in);
		if ((_this.callingDevice_asn == null)
				&& (_this.calledDevice_asn == null)
				&& (_this.trunkGroup == null) && (_this.trunkMember == null)
				&& (_this.lookaheadInfo == null)
				&& (_this.userEnteredCode == null) && (_this.userInfo == null)) {
			return null;
		}
		return _this;
	}

	public static Collection<String> print(LucentOriginalCallInfo _this,
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

		lines.add(_indent + "}");
		return lines;
	}

	short reason;
	CSTAExtendedDeviceID callingDevice_asn;
	CSTAExtendedDeviceID calledDevice_asn;
	String trunkGroup;
	String trunkMember;
	LucentLookaheadInfo lookaheadInfo;

	LucentUserEnteredCode userEnteredCode;

	LucentUserToUserInfo userInfo;

	@Override
	public void decodeMembers(InputStream memberStream) {
		reason = ASNEnumerated.decode(memberStream);
		callingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
		calledDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
		trunkGroup = ASNIA5String.decode(memberStream);
		trunkMember = ASNIA5String.decode(memberStream);
		lookaheadInfo = decodeLookahead(memberStream);
		userEnteredCode = LucentUserEnteredCode.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(reason, memberStream);
		ASNSequence.encode(callingDevice_asn, memberStream);
		ASNSequence.encode(calledDevice_asn, memberStream);
		ASNIA5String.encode(trunkGroup, memberStream);
		ASNIA5String.encode(trunkMember, memberStream);
		encodeLookahead(lookaheadInfo, memberStream);
		LucentUserEnteredCode.encode(userEnteredCode, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
	}

	public CSTAExtendedDeviceID getCalledDevice_asn() {
		return calledDevice_asn;
	}

	public CSTAExtendedDeviceID getCallingDevice_asn() {
		return callingDevice_asn;
	}

	public LucentLookaheadInfo getLookaheadInfo() {
		return lookaheadInfo;
	}

	public short getReason() {
		return reason;
	}

	public String getTrunkGroup() {
		return trunkGroup;
	}

	public String getTrunkMember() {
		return trunkMember;
	}

	public LucentUserEnteredCode getUserEnteredCode() {
		return userEnteredCode;
	}

	public LucentUserToUserInfo getUserToUserInfo() {
		return userInfo;
	}

	public void setCalledDevice_asn(CSTAExtendedDeviceID _calledDevice_asn) {
		calledDevice_asn = _calledDevice_asn;
	}

	public void setCallingDevice_asn(CSTAExtendedDeviceID _callingDevice_asn) {
		callingDevice_asn = _callingDevice_asn;
	}

	public void setLookaheadInfo(LucentLookaheadInfo _lookaheadInfo) {
		lookaheadInfo = _lookaheadInfo;
	}

	public void setReason(short _reason) {
		reason = _reason;
	}

	public void setTrunkGroup(String _trunkGroup) {
		trunkGroup = _trunkGroup;
	}

	public void setTrunkMember(String _trunkMember) {
		trunkMember = _trunkMember;
	}

	public void setUserEnteredCode(LucentUserEnteredCode _userEnteredCode) {
		userEnteredCode = _userEnteredCode;
	}

	public void setUserInfo(LucentUserToUserInfo _userInfo) {
		userInfo = _userInfo;
	}
}

