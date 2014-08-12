package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNNull;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentSelectiveListeningRetrieveConfEvent extends
		LucentPrivateData {
	public static final int PDU = 70;

	static LucentSelectiveListeningRetrieveConfEvent decode(InputStream in) {
		LucentSelectiveListeningRetrieveConfEvent _this = new LucentSelectiveListeningRetrieveConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNNull.encode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentSelectiveListeningRetrieveConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 70;
	}
}