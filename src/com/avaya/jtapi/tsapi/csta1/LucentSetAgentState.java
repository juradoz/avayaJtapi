package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentSetAgentState extends LucentPrivateData {
	public static LucentSetAgentState decode(InputStream in, CSTATSProvider prov) {
		LucentSetAgentState _this = new LucentSetAgentState();
		_this.doDecode(in);

		return _this;
	}

	short workMode;

	static final int PDU = 10;

	public LucentSetAgentState() {
	}

	public LucentSetAgentState(short _workMode) {
		workMode = _workMode;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		workMode = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(workMode, memberStream);
	}

	@Override
	public int getPDU() {
		return 10;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentSetAgentState ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentSetAgentState JD-Core Version: 0.5.4
 */