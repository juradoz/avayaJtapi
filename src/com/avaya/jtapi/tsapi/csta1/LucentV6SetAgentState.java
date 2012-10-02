package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV6SetAgentState extends LucentV5SetAgentState {
	boolean enablePending;
	public static final int PDU = 102;

	public LucentV6SetAgentState() {
	}

	public LucentV6SetAgentState(short _workMode, int _reasonCode,
			boolean _enablePending) {
		super(_workMode, _reasonCode);
		this.enablePending = _enablePending;
	}

	public static LucentSetAgentState decode(InputStream in, CSTATSProvider prov) {
		LucentV6SetAgentState _this = new LucentV6SetAgentState();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNBoolean.encode(this.enablePending, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.enablePending = ASNBoolean.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV6SetAgentState ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentWorkMode.print(this.workMode, "workMode", indent));
		lines.addAll(ASNInteger.print(this.reasonCode, "reasonCode", indent));
		lines.addAll(ASNBoolean.print(this.enablePending, "enablePending",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 102;
	}
}