package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASetDnd extends CSTARequest {
	String device;
	boolean doNotDisturb;
	public static final int PDU = 45;

	public CSTASetDnd() {
	}

	public CSTASetDnd(String _device, boolean _doNotDisturb) {
		this.device = _device;
		this.doNotDisturb = _doNotDisturb;
	}

	public static CSTASetDnd decode(InputStream in) {
		CSTASetDnd _this = new CSTASetDnd();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		this.device = DeviceID.decode(memberStream);
		this.doNotDisturb = ASNBoolean.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.device, memberStream);
		ASNBoolean.encode(this.doNotDisturb, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("CSTASetDnd ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.device, "device", indent));
		lines.addAll(ASNBoolean
				.print(this.doNotDisturb, "doNotDisturb", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 45;
	}

	public String getDevice() {
		return this.device;
	}

	public boolean isDoNotDisturb() {
		return this.doNotDisturb;
	}
}