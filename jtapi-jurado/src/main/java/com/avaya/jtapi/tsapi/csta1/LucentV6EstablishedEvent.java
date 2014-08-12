package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6EstablishedEvent extends LucentV5EstablishedEvent {
	static final int PDU = 118;

	public static LucentEstablishedEvent decode(InputStream in) {
		LucentV6EstablishedEvent _this = new LucentV6EstablishedEvent();
		_this.doDecode(in);

		return _this;
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6EstablishedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
		lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
		lines.addAll(DeviceID.print(this.split_asn, "split", indent));
		lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo,
				"lookaheadInfo", indent));
		lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode,
				"userEnteredCode", indent));
		lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo",
				indent));
		lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
		lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn,
				"distributingDevice", indent));
		lines.addAll(UCID.print(this.ucid, "ucid", indent));
		lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo,
				"callOriginatorInfo", indent));
		lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling",
				indent));
		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 118;
	}
}