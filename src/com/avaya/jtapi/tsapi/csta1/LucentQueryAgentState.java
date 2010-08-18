package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentQueryAgentState extends LucentPrivateData {
	public static LucentQueryAgentState decode(InputStream in) {
		LucentQueryAgentState _this = new LucentQueryAgentState();
		_this.doDecode(in);

		return _this;
	}

	String split;

	public static final int PDU = 16;

	public LucentQueryAgentState() {
	}

	public LucentQueryAgentState(String _split) {
		split = _split;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		split = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(split, memberStream);
	}

	@Override
	public int getPDU() {
		return 16;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryAgentState ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(split, "split", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryAgentState JD-Core Version: 0.5.4
 */