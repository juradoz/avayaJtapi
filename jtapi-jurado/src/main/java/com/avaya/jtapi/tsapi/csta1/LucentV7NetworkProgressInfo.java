package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV7NetworkProgressInfo extends
		LucentV5NetworkProgressInfo {
	LucentDeviceHistoryEntry[] deviceHistory;
	static final int PDU = 136;

	public static LucentNetworkProgressInfo decode(InputStream in) {
		LucentV7NetworkProgressInfo _this = new LucentV7NetworkProgressInfo();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("V7NetworkProgressInfo ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ProgressLocation.print(this.progressLocation,
				"progressLocation", indent));

		lines.addAll(ProgressDescription.print(this.progressDescription,
				"progressDescription", indent));

		lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
		lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
		lines.addAll(CSTADeviceHistoryData.print(getDeviceHistory(),
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 136;
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return this.deviceHistory;
	}

	public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}
}