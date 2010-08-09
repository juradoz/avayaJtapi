package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentV5SetAgentState extends LucentSetAgentState {
	public static LucentSetAgentState decode(InputStream in, CSTATSProvider prov) {
		LucentV5SetAgentState _this = new LucentV5SetAgentState();
		_this.doDecode(in);

		return _this;
	}

	int reasonCode;

	static final int PDU = 87;

	public LucentV5SetAgentState() {
	}

	public LucentV5SetAgentState(short _workMode, int _reasonCode) {
		super(_workMode);
		reasonCode = _reasonCode;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		reasonCode = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNInteger.encode(reasonCode, memberStream);
	}

	@Override
	public int getPDU() {
		return 87;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentV5SetAgentState ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(workMode, "workMode", indent));
		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV5SetAgentState JD-Core Version: 0.5.4
 */