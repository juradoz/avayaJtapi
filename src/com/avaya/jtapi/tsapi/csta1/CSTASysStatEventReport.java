package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class CSTASysStatEventReport extends CSTAEventReport {
	public static final int PDU = 106;

	public static CSTASysStatEventReport decode(InputStream in) {
		CSTASysStatEventReport _this = new CSTASysStatEventReport();
		_this.doDecode(in);
		return _this;
	}

	private short state;

	@Override
	public void decodeMembers(InputStream memberStream) {
		state = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("CSTASysStatEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.addAll(SystemStatus.print(state, "state", indent));
		lines.add("}");
		return lines;
	}

	public void setState(short state) {
		this.state = state;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASysStatEventReport JD-Core Version: 0.5.4
 */