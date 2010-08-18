package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTASetFwd extends CSTARequest {
	String device;
	CSTAForwardingInfo forward;
	public static final int PDU = 47;

	public static CSTASetFwd decode(InputStream in) {
		CSTASetFwd _this = new CSTASetFwd();
		_this.doDecode(in);

		return _this;
	}

	public CSTASetFwd() {
	}

	public CSTASetFwd(String _device, CSTAForwardingInfo _forward) {
		device = _device;
		forward = _forward;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
		forward = CSTAForwardingInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
		ASNSequence.encode(forward, memberStream);
	}

	public String getDevice() {
		return device;
	}

	public CSTAForwardingInfo getForward() {
		return forward;
	}

	@Override
	public int getPDU() {
		return 47;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASetFwd ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));
		lines.addAll(CSTAForwardingInfo.print(forward, "forward", indent));

		lines.add("}");
		return lines;
	}
}

