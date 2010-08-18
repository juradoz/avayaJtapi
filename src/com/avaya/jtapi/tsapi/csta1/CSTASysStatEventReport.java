package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class CSTASysStatEventReport extends CSTAEventReport {
	public static final int PDU = 106;

	public static CSTASysStatEventReport decode(final InputStream in) {
		final CSTASysStatEventReport _this = new CSTASysStatEventReport();
		_this.doDecode(in);
		return _this;
	}

	private short state;

	@Override
	public void decodeMembers(final InputStream memberStream) {
		state = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(state, memberStream);
	}

	@Override
	public int getPDU() {
		return 106;
	}

	public short getState() {
		return state;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASysStatEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.addAll(SystemStatus.print(state, "state", indent));
		lines.add("}");
		return lines;
	}

	public void setState(final short state) {
		this.state = state;
	}
}
