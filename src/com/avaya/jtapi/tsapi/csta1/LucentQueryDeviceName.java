package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentQueryDeviceName extends LucentPrivateData {
	String device;
	public static final int PDU = 49;

	public static LucentQueryDeviceName decode(final InputStream in) {
		final LucentQueryDeviceName _this = new LucentQueryDeviceName();
		_this.doDecode(in);

		return _this;
	}

	public LucentQueryDeviceName() {
	}

	public LucentQueryDeviceName(final String _device) {
		device = _device;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
	}

	public String getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 49;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryDeviceName ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));

		lines.add("}");
		return lines;
	}
}
