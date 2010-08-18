package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class LucentAgentModeChangeEvent extends LucentPrivateData {
	int reasonCode;
	static final int PDU = 124;

	static LucentAgentModeChangeEvent decode(final InputStream in) {
		final LucentAgentModeChangeEvent _this = new LucentAgentModeChangeEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		reasonCode = ASNInteger.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentAgentModeChangeEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(reasonCode, "reasonCode", indent));
		lines.add("}");
		return lines;
	}

	public void setReasonCode(final int reasonCode) {
		this.reasonCode = reasonCode;
	}
}
