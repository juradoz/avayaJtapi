package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentDivertedEvent extends LucentPrivateData {
	private LucentDeviceHistoryEntry[] deviceHistory;
	static final int PDU = 135;

	static LucentDivertedEvent decode(InputStream in) {
		LucentDivertedEvent _this = new LucentDivertedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		deviceHistory = CSTADeviceHistoryData.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		CSTADeviceHistoryData.encode(deviceHistory, memberStream);
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return deviceHistory;
	}

	@Override
	public int getPDU() {
		return 135;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentDivertedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTADeviceHistoryData.print(deviceHistory,
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}
}

