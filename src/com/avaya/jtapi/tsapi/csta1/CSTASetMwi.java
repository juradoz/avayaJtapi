package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class CSTASetMwi extends CSTARequest {
	String device;
	boolean messages;
	static final int PDU = 43;

	public CSTASetMwi(final String _device, final boolean _messages) {
		device = _device;
		messages = _messages;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
		ASNBoolean.encode(messages, memberStream);
	}

	public String getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 43;
	}

	public boolean isMessages() {
		return messages;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASetMwi ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));
		lines.addAll(ASNBoolean.print(messages, "messages", indent));

		lines.add("}");
		return lines;
	}
}
