package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentCallClearedEvent extends LucentPrivateData {
	short reason;
	static final int PDU = 34;

	public static LucentCallClearedEvent decode(final InputStream in) {
		final LucentCallClearedEvent _this = new LucentCallClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		reason = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(reason, memberStream);
	}

	@Override
	public int getPDU() {
		return 34;
	}

	public short getReason() {
		return reason;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentCallClearedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentReasonCode.print(reason, "reason", indent));

		lines.add("}");
		return lines;
	}

	public void setReason(final short reason) {
		this.reason = reason;
	}
}
