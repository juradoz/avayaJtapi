package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public class CSTASysStatStartConfEvent extends CSTAConfirmation {
	private int filter;
	public static final int PDU = 101;

	public static CSTASysStatStartConfEvent decode(final InputStream in) {
		final CSTASysStatStartConfEvent _this = new CSTASysStatStartConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		filter = ASNBitString.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		SystemStatusFilter.encode(filter, memberStream);
	}

	public int getFilter() {
		return filter;
	}

	@Override
	public int getPDU() {
		return 101;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTASysStatStartConfEvent ::=");
		lines.add("{");
		final String indent = "  ";
		lines.addAll(SystemStatusFilter.print(filter, "filter", indent));
		lines.add("}");
		return lines;
	}

	public void setFilter(final int filter) {
		this.filter = filter;
	}
}
