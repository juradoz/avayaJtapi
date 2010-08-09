package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public class LucentDeliveredEvent extends LucentPrivateData {
	short deliveredType;
	String trunkGroup;
	String trunkMember;
	String split_asn;
	LucentLookaheadInfo lookaheadInfo;
	LucentUserEnteredCode userEnteredCode;
	LucentUserToUserInfo userInfo;
	short reason;
	LucentOriginalCallInfo originalCallInfo;
	CSTAExtendedDeviceID distributingDevice_asn;
	static final int PDU = 60;

	public static LucentDeliveredEvent decode(InputStream in) {
		LucentDeliveredEvent _this = new LucentDeliveredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		deliveredType = ASNEnumerated.decode(memberStream);
		trunkGroup = ASNIA5String.decode(memberStream);
		trunkMember = ASNIA5String.decode(memberStream);
		split_asn = ASNIA5String.decode(memberStream);
		lookaheadInfo = decodeLookahead(memberStream);
		userEnteredCode = LucentUserEnteredCode.decode(memberStream);
		userInfo = LucentUserToUserInfo.decode(memberStream);
		reason = ASNEnumerated.decode(memberStream);
		originalCallInfo = decodeOCI(memberStream);
		distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(deliveredType, memberStream);
		ASNIA5String.encode(trunkGroup, memberStream);
		ASNIA5String.encode(trunkMember, memberStream);
		ASNIA5String.encode(split_asn, memberStream);
		encodeLookahead(lookaheadInfo, memberStream);
		LucentUserEnteredCode.encode(userEnteredCode, memberStream);
		LucentUserToUserInfo.encode(userInfo, memberStream);
		ASNEnumerated.encode(reason, memberStream);
		encodeOCI(originalCallInfo, memberStream);
		ASNSequence.encode(distributingDevice_asn, memberStream);
	}

	public short getDeliveredType() {
		return deliveredType;
	}

	public CSTAExtendedDeviceID getDistributingDevice_asn() {
		return distributingDevice_asn;
	}

	public LucentLookaheadInfo getLookaheadInfo() {
		return lookaheadInfo;
	}

	public LucentOriginalCallInfo getOriginalCallInfo() {
		return originalCallInfo;
	}

	@Override
	public int getPDU() {
		return 60;
	}

	public short getReason() {
		return reason;
	}

	public String getSplit_asn() {
		return split_asn;
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

	public LucentUserToUserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentDeliveredEvent ::=");
		lines.add("{");

		String indent = "  ";

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
		lines.addAll(LucentOriginalCallInfo.print(originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));

		lines.add("}");
		return lines;
	}

	public void setDeliveredType(short deliveredType) {
		this.deliveredType = deliveredType;
	}

	public void setDistributingDevice_asn(
			CSTAExtendedDeviceID distributingDevice_asn) {
		this.distributingDevice_asn = distributingDevice_asn;
	}

	public void setLookaheadInfo(LucentLookaheadInfo lookaheadInfo) {
		this.lookaheadInfo = lookaheadInfo;
	}

	public void setOriginalCallInfo(LucentOriginalCallInfo originalCallInfo) {
		this.originalCallInfo = originalCallInfo;
	}

	public void setReason(short reason) {
		this.reason = reason;
	}

	public void setSplit_asn(String split_asn) {
		this.split_asn = split_asn;
	}

	public void setTrunkGroup(String trunkGroup) {
		this.trunkGroup = trunkGroup;
	}

	public void setTrunkMember(String trunkMember) {
		this.trunkMember = trunkMember;
	}

	public void setUserEnteredCode(LucentUserEnteredCode userEnteredCode) {
		this.userEnteredCode = userEnteredCode;
	}

	public void setUserInfo(LucentUserToUserInfo userInfo) {
		this.userInfo = userInfo;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentDeliveredEvent JD-Core Version: 0.5.4
 */