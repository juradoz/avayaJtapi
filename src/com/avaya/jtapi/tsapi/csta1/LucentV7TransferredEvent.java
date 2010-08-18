package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class LucentV7TransferredEvent extends LucentV6TransferredEvent {
	private LucentDeviceHistoryEntry[] deviceHistory;
	CSTAExtendedDeviceID distributingVDN_asn;
	static final int PDU = 132;

	public static LucentTransferredEvent decode(final InputStream in) {
		final LucentV7TransferredEvent _this = new LucentV7TransferredEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		deviceHistory = CSTADeviceHistoryData.decode(memberStream);
		distributingVDN_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	@Override
	public LucentOriginalCallInfo decodeOCI(final InputStream memberStream) {
		return LucentV7OriginalCallInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
		CSTADeviceHistoryData.encode(deviceHistory, memberStream);
		ASNSequence.encode(distributingVDN_asn, memberStream);
	}

	@Override
	public void encodeOCI(final LucentOriginalCallInfo callInfo,
			final OutputStream memberStream) {
		ASNSequence.encode(callInfo, memberStream);
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return deviceHistory;
	}

	public CSTAExtendedDeviceID getDistributingVDN_asn() {
		return distributingVDN_asn;
	}

	@Override
	public int getPDU() {
		return 132;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("LucentV7TransferredEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentOriginalCallInfo.print(originalCallInfo,
				"originalCallInfo", indent));

		lines.addAll(CSTAExtendedDeviceID.print(distributingDevice_asn,
				"distributingDevice", indent));

		lines.addAll(ASNIA5String.print(ucid, "ucid", indent));
		lines.addAll(LucentTrunkInfoList.print(lucentTrunkInfo,
				"lucentTrunkInfo", indent));
		lines.addAll(CSTADeviceHistoryData.print(deviceHistory,
				"deviceHistory", indent));
		lines.addAll(CSTAExtendedDeviceID.print(distributingVDN_asn,
				"distributingVDN", indent));

		lines.add("}");
		return lines;
	}

	public void setDeviceHistory(final LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}

	public void setDistributingVDN_asn(
			final CSTAExtendedDeviceID distributingVDN_asn) {
		this.distributingVDN_asn = distributingVDN_asn;
	}
}
