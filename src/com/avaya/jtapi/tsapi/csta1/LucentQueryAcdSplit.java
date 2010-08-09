package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentQueryAcdSplit extends LucentPrivateData {
	public static LucentQueryAcdSplit decode(InputStream in) {
		LucentQueryAcdSplit _this = new LucentQueryAcdSplit();
		_this.doDecode(in);

		return _this;
	}

	String device;

	public static final int PDU = 11;

	public LucentQueryAcdSplit() {
	}

	public LucentQueryAcdSplit(String _device) {
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
		return 11;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("LucentQueryAcdSplit ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryAcdSplit JD-Core Version: 0.5.4
 */