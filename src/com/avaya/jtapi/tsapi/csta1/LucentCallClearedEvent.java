package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentCallClearedEvent extends LucentPrivateData {
	short reason;
	static final int PDU = 34;

	public static LucentCallClearedEvent decode(InputStream in) {
		LucentCallClearedEvent _this = new LucentCallClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.reason = LucentReasonCode.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		LucentReasonCode.encode(this.reason, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentCallClearedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 34;
	}

	public short getReason() {
		return this.reason;
	}

	public void setReason(short reason) {
		this.reason = reason;
	}
}