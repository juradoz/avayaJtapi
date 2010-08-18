package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV7OriginalCallInfo extends LucentV5OriginalCallInfo {
	public static LucentOriginalCallInfo decode(InputStream in) {
		LucentV7OriginalCallInfo _this = new LucentV7OriginalCallInfo();
		_this.doDecode(in);
		if ((_this.callingDevice_asn == null)
				&& (_this.calledDevice_asn == null)
				&& (_this.trunkGroup == null) && (_this.trunkMember == null)
				&& (_this.lookaheadInfo == null)
				&& (_this.userEnteredCode == null) && (_this.userInfo == null)
				&& (_this.ucid == null) && (_this.callOriginatorInfo == null)
				&& (_this.asn_deviceHistory == null)) {
			return null;
		}
		return _this;
	}

	public static Collection<String> print(LucentV7OriginalCallInfo _this,
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
		lines.addAll(CSTADeviceHistoryData.print(_this.asn_deviceHistory,
				"deviceHistoryEntry", indent));

		lines.add(_indent + "}");
		return lines;
	}

	private LucentDeviceHistoryEntry[] asn_deviceHistory;

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		asn_deviceHistory = CSTADeviceHistoryData.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		CSTADeviceHistoryData.encode(asn_deviceHistory, memberStream);
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return asn_deviceHistory;
	}

	public boolean hasDeviceHistory() {
		return asn_deviceHistory != null;
	}

	public void setDeviceHistory(LucentDeviceHistoryEntry[] asn_deviceHistory) {
		this.asn_deviceHistory = asn_deviceHistory;
	}
}

