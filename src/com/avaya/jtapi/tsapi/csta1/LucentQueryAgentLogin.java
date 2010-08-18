package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentQueryAgentLogin extends LucentPrivateData {
	public static LucentQueryAgentLogin decode(InputStream in) {
		LucentQueryAgentLogin _this = new LucentQueryAgentLogin();
		_this.doDecode(in);

		return _this;
	}

	String device;

	public static final int PDU = 13;

	public LucentQueryAgentLogin() {
	}

	public LucentQueryAgentLogin(String _device) {
		device = _device;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
	}

	@Override
	public int getPDU() {
		return 13;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentLogin ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));

		lines.add("}");
		return lines;
	}
}

