package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentOriginalCallInfo extends LucentPrivateData {
	public static final short OR_NONE = 0;
	public static final short OR_CONSULTATION = 1;
	public static final short OR_CONFERENCED = 2;
	public static final short OR_TRANSFERRED = 3;
	public static final short OR_NEW_CALL = 4;
	short reason;
	CSTAExtendedDeviceID callingDevice_asn;
	CSTAExtendedDeviceID calledDevice_asn;
	String trunkGroup;
	String trunkMember;
	LucentLookaheadInfo lookaheadInfo;
	LucentUserEnteredCode userEnteredCode;
	LucentUserToUserInfo userInfo;

	public short getReason() {
		return this.reason;
	}

	public void setReason(short _reason) {
		this.reason = _reason;
	}

	public LucentUserToUserInfo getUserToUserInfo() {
		return this.userInfo;
	}

	public LucentLookaheadInfo getLookaheadInfo() {
		return this.lookaheadInfo;
	}

	public LucentUserEnteredCode getUserEnteredCode() {
		return this.userEnteredCode;
	}

	public CSTAExtendedDeviceID getCalledDevice_asn() {
		return this.calledDevice_asn;
	}

	public CSTAExtendedDeviceID getCallingDevice_asn() {
		return this.callingDevice_asn;
	}

	public String getTrunkGroup() {
		return this.trunkGroup;
	}

	public String getTrunkMember() {
		return this.trunkMember;
	}

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

	public void encodeMembers(OutputStream memberStream) {
		ReasonForCallInfo.encode(this.reason, memberStream);
		CSTAExtendedDeviceID.encode(this.callingDevice_asn, memberStream);
		CSTAExtendedDeviceID.encode(this.calledDevice_asn, memberStream);
		DeviceID.encode(this.trunkGroup, memberStream);
		DeviceID.encode(this.trunkMember, memberStream);
		encodeLookahead(this.lookaheadInfo, memberStream);
		LucentUserEnteredCode.encode(this.userEnteredCode, memberStream);
		LucentUserToUserInfo.encode(this.userInfo, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.reason = ReasonForCallInfo.decode(memberStream);
		this.callingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
		this.calledDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
		this.trunkGroup = DeviceID.decode(memberStream);
		this.trunkMember = DeviceID.decode(memberStream);
		this.lookaheadInfo = decodeLookahead(memberStream);
		this.userEnteredCode = LucentUserEnteredCode.decode(memberStream);
		this.userInfo = LucentUserToUserInfo.decode(memberStream);
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
		lines.addAll(DeviceID.print(_this.trunkGroup, "trunkGroup", indent));
		lines.addAll(DeviceID.print(_this.trunkMember, "trunkMember", indent));
		lines.addAll(LucentLookaheadInfo.print(_this.lookaheadInfo,
				"lookaheadInfo", indent));
		lines.addAll(LucentUserEnteredCode.print(_this.userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(_this.userInfo, "userInfo",
				indent));

		lines.add(_indent + "}");
		return lines;
	}

	public void setCalledDevice_asn(CSTAExtendedDeviceID _calledDevice_asn) {
		this.calledDevice_asn = _calledDevice_asn;
	}

	public void setCallingDevice_asn(CSTAExtendedDeviceID _callingDevice_asn) {
		this.callingDevice_asn = _callingDevice_asn;
	}

	public void setLookaheadInfo(LucentLookaheadInfo _lookaheadInfo) {
		this.lookaheadInfo = _lookaheadInfo;
	}

	public void setTrunkGroup(String _trunkGroup) {
		this.trunkGroup = _trunkGroup;
	}

	public void setTrunkMember(String _trunkMember) {
		this.trunkMember = _trunkMember;
	}

	public void setUserEnteredCode(LucentUserEnteredCode _userEnteredCode) {
		this.userEnteredCode = _userEnteredCode;
	}

	public void setUserInfo(LucentUserToUserInfo _userInfo) {
		this.userInfo = _userInfo;
	}
}