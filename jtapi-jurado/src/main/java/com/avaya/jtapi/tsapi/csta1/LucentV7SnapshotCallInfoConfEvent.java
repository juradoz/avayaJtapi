package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV7SnapshotCallInfoConfEvent extends LucentPrivateData {
	LucentDeviceHistoryEntry[] deviceHistory;
	static final int PDU = 138;

	static LucentV7SnapshotCallInfoConfEvent decode(InputStream in) {
		LucentV7SnapshotCallInfoConfEvent _this = new LucentV7SnapshotCallInfoConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV7SnapshotCallInfoConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory,
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 138;
	}
}