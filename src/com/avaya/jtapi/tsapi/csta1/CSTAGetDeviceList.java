package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;

public class CSTAGetDeviceList extends CSTARequest {
	int index;
	short level;
	static final int PDU = 126;

	public CSTAGetDeviceList(int _index, short _level) {
		index = _index;
		level = _level;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(index, memberStream);
		ASNEnumerated.encode(level, memberStream);
	}

	public int getIndex() {
		return index;
	}

	public short getLevel() {
		return level;
	}

	@Override
	public int getPDU() {
		return 126;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("CSTAGetDeviceList ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(index, "index", indent));
		lines.addAll(CSTALevel.print(level, "level", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAGetDeviceList JD-Core Version: 0.5.4
 */