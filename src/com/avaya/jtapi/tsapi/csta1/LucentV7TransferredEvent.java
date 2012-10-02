package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV7TransferredEvent extends LucentV6TransferredEvent {
	private LucentDeviceHistoryEntry[] deviceHistory;
	CSTAExtendedDeviceID distributingVDN_asn;
	static final int PDU = 132;

	public static LucentTransferredEvent decode(InputStream in) {
		LucentV7TransferredEvent _this = new LucentV7TransferredEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
		this.distributingVDN_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
		CSTAExtendedDeviceID.encode(this.distributingVDN_asn, memberStream);
	}

	public LucentOriginalCallInfo decodeOCI(InputStream memberStream) {
		return LucentV7OriginalCallInfo.decode(memberStream);
	}

	public void encodeOCI(LucentOriginalCallInfo callInfo,
			OutputStream memberStream) {
		LucentV7OriginalCallInfo.encode(callInfo, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("LucentV7TransferredEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentV7OriginalCallInfo.print(this.originalCallInfo,
				"originalCallInfo", indent));

		lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn,
				"distributingDevice", indent));

		lines.addAll(UCID.print(this.ucid, "ucid", indent));
		lines.addAll(LucentTrunkInfoList.print(this.lucentTrunkInfo,
				"lucentTrunkInfo", indent));
		lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory,
				"deviceHistory", indent));
		lines.addAll(CSTAExtendedDeviceID.print(this.distributingVDN_asn,
				"distributingVDN", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 132;
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return this.deviceHistory;
	}

	public CSTAExtendedDeviceID getDistributingVDN_asn() {
		return this.distributingVDN_asn;
	}

	public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}

	public void setDistributingVDN_asn(CSTAExtendedDeviceID distributingVDN_asn) {
		this.distributingVDN_asn = distributingVDN_asn;
	}
}