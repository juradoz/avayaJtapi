package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNNull;

public class CSTASysStatStopConfEvent extends CSTAConfirmation {
	public static final int PDU = 103;

	public static CSTASysStatStopConfEvent decode(final InputStream in) {
		final CSTASysStatStopConfEvent _this = new CSTASysStatStopConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		ASNNull.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNNull.encode(memberStream);
	}

	@Override
	public int getPDU() {
		return 103;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTASysStatStopConfEvent ::=");
		lines.add("{");
		final String indent = "  ";
		lines.addAll(ASNNull.print(indent));
		lines.add("}");
		return lines;
	}
}
