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

	public CSTAGetDeviceList(final int _index, final short _level) {
		index = _index;
		level = _level;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAGetDeviceList ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNInteger.print(index, "index", indent));
		lines.addAll(CSTALevel.print(level, "level", indent));

		lines.add("}");
		return lines;
	}
}
