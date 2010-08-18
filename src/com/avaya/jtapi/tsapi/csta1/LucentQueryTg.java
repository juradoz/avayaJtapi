package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentQueryTg extends LucentPrivateData {
	public static LucentQueryTg decode(final InputStream in) {
		final LucentQueryTg _this = new LucentQueryTg();
		_this.doDecode(in);

		return _this;
	}

	String device;

	static final int PDU = 26;

	public LucentQueryTg() {
	}

	public LucentQueryTg(final String _device) {
		device = _device;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
	}

	@Override
	public int getPDU() {
		return 26;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryTg ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));

		lines.add("}");
		return lines;
	}
}
