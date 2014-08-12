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

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.name = UnicodeDeviceID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		UnicodeDeviceID.encode(this.name, memberStream);
	}

	public int getPDU() {
		return 89;
	}
}