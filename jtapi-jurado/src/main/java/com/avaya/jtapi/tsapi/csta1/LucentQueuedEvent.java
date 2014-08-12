package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentQueuedEvent extends LucentPrivateData {
	private LucentDeviceHistoryEntry[] deviceHistory;
	static final int PDU = 130;

	static LucentQueuedEvent decode(InputStream in) {
		LucentQueuedEvent _this = new LucentQueuedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueuedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory,
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 130;
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return this.deviceHistory;
	}

	public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}
}