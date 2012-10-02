package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class LucentV5SetAgentState extends LucentSetAgentState {
	int reasonCode;
	static final int PDU = 87;

	public LucentV5SetAgentState() {
	}

	public LucentV5SetAgentState(short _workMode, int _reasonCode) {
		super(_workMode);
		this.reasonCode = _reasonCode;
	}

	public static LucentSetAgentState decode(InputStream in, CSTATSProvider prov) {
		LucentV5SetAgentState _this = new LucentV5SetAgentState();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.reasonCode = ASNInteger.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNInteger.encode(this.reasonCode, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5SetAgentState ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
		lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 87;
	}
}