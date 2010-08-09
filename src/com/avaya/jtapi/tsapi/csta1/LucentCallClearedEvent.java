package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentCallClearedEvent extends LucentPrivateData {
	short reason;
	static final int PDU = 34;

	public static LucentCallClearedEvent decode(InputStream in) {
		LucentCallClearedEvent _this = new LucentCallClearedEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		reason = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("LucentCallClearedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentReasonCode.print(reason, "reason", indent));

		lines.add("}");
		return lines;
	}

	public void setReason(short reason) {
		this.reason = reason;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentCallClearedEvent JD-Core Version: 0.5.4
 */