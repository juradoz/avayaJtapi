package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public class CSTASysStatStartConfEvent extends CSTAConfirmation {
	private int filter;
	public static final int PDU = 101;

	public static CSTASysStatStartConfEvent decode(InputStream in) {
		CSTASysStatStartConfEvent _this = new CSTASysStatStartConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		filter = ASNBitString.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();
		lines.add("CSTASysStatStartConfEvent ::=");
		lines.add("{");
		String indent = "  ";
		lines.addAll(SystemStatusFilter.print(filter, "filter", indent));
		lines.add("}");
		return lines;
	}

	public void setFilter(int filter) {
		this.filter = filter;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASysStatStartConfEvent JD-Core Version: 0.5.4
 */