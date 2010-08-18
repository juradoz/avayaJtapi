package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentAgentModeChangeEvent extends LucentPrivateData {
	int reasonCode;
	static final int PDU = 124;

	static LucentAgentModeChangeEvent decode(InputStream in) {
		LucentAgentModeChangeEvent _this = new LucentAgentModeChangeEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		reasonCode = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(reasonCode, memberStream);
	}

	@Override
	public int getPDU() {
		return 124;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentAgentModeChangeEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));
		lines.add("}");
		return lines;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}
}

