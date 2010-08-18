package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentV7NetworkProgressInfo extends
		LucentV5NetworkProgressInfo {
	LucentDeviceHistoryEntry[] deviceHistory;
	static final int PDU = 136;

	public static LucentNetworkProgressInfo decode(InputStream in) {
		LucentV7NetworkProgressInfo _this = new LucentV7NetworkProgressInfo();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		deviceHistory = CSTADeviceHistoryData.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		CSTADeviceHistoryData.encode(deviceHistory, memberStream);
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return deviceHistory;
	}

	@Override
	public int getPDU() {
		return 136;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("V7NetworkProgressInfo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ProgressLocation.print(progressLocation,
				"progressLocation", indent));

		lines.addAll(ProgressDescription.print(progressDescription,
				"progressDescription", indent));

		lines.addAll(ASNIA5String.print(trunkGroup, "trunkGroup", indent));
		lines.addAll(ASNIA5String.print(trunkMember, "trunkMember", indent));
		lines.addAll(CSTADeviceHistoryData.print(getDeviceHistory(),
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}
}
