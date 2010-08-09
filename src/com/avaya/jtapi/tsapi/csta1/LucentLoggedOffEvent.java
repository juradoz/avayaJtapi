package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public final class LucentLoggedOffEvent extends LucentPrivateData {
	int reasonCode;
	static final int PDU = 79;

	static LucentLoggedOffEvent decode(InputStream in) {
		LucentLoggedOffEvent _this = new LucentLoggedOffEvent();
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
		return 79;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("LucentLoggedOffEvent ::=");
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentLoggedOffEvent JD-Core Version: 0.5.4
 */