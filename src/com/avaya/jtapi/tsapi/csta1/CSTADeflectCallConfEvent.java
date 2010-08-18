package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNNull;

public final class CSTADeflectCallConfEvent extends CSTAConfirmation {
	public static final int PDU = 16;

	public static CSTADeflectCallConfEvent decode(InputStream in) {
		CSTADeflectCallConfEvent _this = new CSTADeflectCallConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNNull.encode(memberStream);
	}

	@Override
	public int getPDU() {
		return 16;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADeflectCallConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNNull.print(indent));

		lines.add("}");
		return lines;
	}
}

