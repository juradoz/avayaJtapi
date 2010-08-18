package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public class CSTASysStatStart extends CSTARequest {
	public static CSTASysStatStart decode(InputStream in) {
		CSTASysStatStart _this = new CSTASysStatStart();
		_this.doDecode(in);
		return _this;
	}

	private int statusFilter;

	public static final int PDU = 100;

	public CSTASysStatStart() {
		statusFilter = 0;
	}

	public CSTASysStatStart(int filter) {
		statusFilter = filter;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		statusFilter = ASNBitString.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		SystemStatusFilter.encode(statusFilter, memberStream);
	}

	@Override
	public int getPDU() {
		return 100;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTASysStatStart ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(SystemStatusFilter.print(statusFilter, "statusFilter",
				indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASysStatStart JD-Core Version: 0.5.4
 */