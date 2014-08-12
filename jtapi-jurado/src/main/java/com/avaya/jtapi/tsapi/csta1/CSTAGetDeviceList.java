package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class CSTAGetDeviceList extends CSTARequest {
	int index;
	short level;
	static final int PDU = 126;

	public CSTAGetDeviceList(int _index, short _level) {
		this.index = _index;
		this.level = _level;
	}

	public void encodeMembers(OutputStream memberStream) {
		ASNInteger.encode(this.index, memberStream);
		CSTALevel.encode(this.level, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAGetDeviceList ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNInteger.print(this.index, "index", indent));
		lines.addAll(CSTALevel.print(this.level, "level", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 126;
	}

	public int getIndex() {
		return this.index;
	}

	public short getLevel() {
		return this.level;
	}
}