package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;

public class LucentV5QueryDeviceNameConfEvent extends
		LucentQueryDeviceNameConfEvent {
	static final int PDU = 89;

	public static LucentQueryDeviceNameConfEvent decode(InputStream in) {
		LucentV5QueryDeviceNameConfEvent _this = new LucentV5QueryDeviceNameConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		name = UnicodeDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		UnicodeDeviceID.encode(name, memberStream);
	}

	@Override
	public int getPDU() {
		return 89;
	}
}
