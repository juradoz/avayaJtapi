package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;

public final class LucentV7QueryDeviceNameConfEvent extends
		LucentV5QueryDeviceNameConfEvent {
	public static final int PDU = 125;

	public static LucentQueryDeviceNameConfEvent decode(InputStream in) {
		LucentV7QueryDeviceNameConfEvent _this = new LucentV7QueryDeviceNameConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
	}

	@Override
	public int getPDU() {
		return 125;
	}
}
