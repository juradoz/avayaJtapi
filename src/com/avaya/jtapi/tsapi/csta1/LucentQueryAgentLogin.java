package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentQueryAgentLogin extends LucentPrivateData {
	public static LucentQueryAgentLogin decode(final InputStream in) {
		final LucentQueryAgentLogin _this = new LucentQueryAgentLogin();
		_this.doDecode(in);

		return _this;
	}

	String device;

	public static final int PDU = 13;

	public LucentQueryAgentLogin() {
	}

	public LucentQueryAgentLogin(final String _device) {
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

	@Override
	public int getPDU() {
		return 13;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentLogin ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));

		lines.add("}");
		return lines;
	}
}
