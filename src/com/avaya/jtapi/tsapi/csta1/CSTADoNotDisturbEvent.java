package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTADoNotDisturbEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID device;
	boolean doNotDisturbOn;
	public static final int PDU = 69;

	public static CSTADoNotDisturbEvent decode(InputStream in) {
		CSTADoNotDisturbEvent _this = new CSTADoNotDisturbEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		device = CSTAExtendedDeviceID.decode(memberStream);
		doNotDisturbOn = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNSequence.encode(device, memberStream);
		ASNBoolean.encode(doNotDisturbOn, memberStream);
	}

	public CSTAExtendedDeviceID getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 69;
	}

	public boolean isDoNotDisturbOn() {
		return doNotDisturbOn;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADoNotDisturbEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(device, "device", indent));
		lines
				.addAll(ASNBoolean.print(doNotDisturbOn, "doNotDisturbOn",
						indent));

		lines.add("}");
		return lines;
	}

	public void setDevice(CSTAExtendedDeviceID device) {
		this.device = device;
	}

	public void setDoNotDisturbOn(boolean doNotDisturb) {
		doNotDisturbOn = doNotDisturb;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTADoNotDisturbEvent JD-Core Version: 0.5.4
 */