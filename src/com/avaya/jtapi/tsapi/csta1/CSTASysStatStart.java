package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public class CSTASysStatStart extends CSTARequest {
	public static CSTASysStatStart decode(final InputStream in) {
		final CSTASysStatStart _this = new CSTASysStatStart();
		_this.doDecode(in);
		return _this;
	}

	private int statusFilter;

	public static final int PDU = 100;

	public CSTASysStatStart() {
		statusFilter = 0;
	}

	public CSTASysStatStart(final int filter) {
		statusFilter = filter;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		statusFilter = ASNBitString.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		SystemStatusFilter.encode(statusFilter, memberStream);
	}

	@Override
	public int getPDU() {
		return 100;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTASysStatStart ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(SystemStatusFilter.print(statusFilter, "statusFilter",
				indent));

		lines.add("}");
		return lines;
	}
}
