package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV7ConnectionClearedEvent extends
		LucentV6ConnectionClearedEvent {
	private LucentDeviceHistoryEntry[] deviceHistory;
	static final int PDU = 134;

	public static LucentConnectionClearedEvent decode(final InputStream in) {
		final LucentV7ConnectionClearedEvent _this = new LucentV7ConnectionClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		deviceHistory = CSTADeviceHistoryData.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		super.encodeMembers(memberStream);
		CSTADeviceHistoryData.encode(deviceHistory, memberStream);
	}

	public LucentDeviceHistoryEntry[] getDeviceHistory() {
		return deviceHistory;
	}

	@Override
	public int getPDU() {
		return 134;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV7ConnectionClearedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentUserToUserInfo.print(userInfo, "userInfo", indent));
		lines.addAll(CSTADeviceHistoryData.print(deviceHistory,
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public void setDeviceHistory(final LucentDeviceHistoryEntry[] deviceHistory) {
		this.deviceHistory = deviceHistory;
	}
}
