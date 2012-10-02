package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV6DeliveredEvent extends LucentV5DeliveredEvent {
	static final int PDU = 117;

	public static LucentDeliveredEvent decode(InputStream in) {
		LucentV6DeliveredEvent _this = new LucentV6DeliveredEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6DeliveredEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentDeliveredType.print(this.deliveredType,
				"deliveredType", indent));
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
		lines.addAll(UCID.print(this.ucid, "ucid", indent));
		lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo,
				"callOriginatorInfo", indent));
		lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo,
				"originalCallInfo", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn,
				"distributingDevice", indent));
		lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling",
				indent));
		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 117;
	}
}