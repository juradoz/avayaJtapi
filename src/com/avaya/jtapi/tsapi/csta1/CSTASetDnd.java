package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTASetDnd extends CSTARequest {
	String device;
	boolean doNotDisturb;
	public static final int PDU = 45;

	public static CSTASetDnd decode(InputStream in) {
		CSTASetDnd _this = new CSTASetDnd();
		_this.doDecode(in);

		return _this;
	}

	public CSTASetDnd() {
	}

	public CSTASetDnd(String _device, boolean _doNotDisturb) {
		device = _device;
		doNotDisturb = _doNotDisturb;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
		doNotDisturb = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
		ASNBoolean.encode(doNotDisturb, memberStream);
	}

	public String getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 45;
	}

	public boolean isDoNotDisturb() {
		return doNotDisturb;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();

		lines.add("CSTASetDnd ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));
		lines.addAll(ASNBoolean.print(doNotDisturb, "doNotDisturb", indent));

		lines.add("}");
		return lines;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASetDnd JD-Core Version: 0.5.4
 */