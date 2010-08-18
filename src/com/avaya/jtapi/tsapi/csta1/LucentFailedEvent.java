package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentFailedEvent extends LucentPrivateData {
	LucentDeviceHistoryEntry[] deviceHistory;
	static final int PDU = 137;

	static LucentFailedEvent decode(InputStream in) {
		LucentFailedEvent _this = new LucentFailedEvent();
		_this.doDecode(in);

		return _this;
	}

	LucentFailedEvent() {
	}

	LucentFailedEvent(LucentDeviceHistoryEntry[] _deviceHistory) {
		deviceHistory = _deviceHistory;
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
		return 137;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentFailedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTADeviceHistoryData.print(deviceHistory,
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public void setDeviceHistory(LucentDeviceHistoryEntry[] entry) {
		deviceHistory = entry;
	}
}

