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

	public static CSTADoNotDisturbEvent decode(final InputStream in) {
		final CSTADoNotDisturbEvent _this = new CSTADoNotDisturbEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		device = CSTAExtendedDeviceID.decode(memberStream);
		doNotDisturbOn = ASNBoolean.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTADoNotDisturbEvent ::=");
		lines.add("{");

		final String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(device, "device", indent));
		lines.addAll(ASNBoolean.print(doNotDisturbOn, "doNotDisturbOn", indent));

		lines.add("}");
		return lines;
	}

	public void setDevice(final CSTAExtendedDeviceID device) {
		this.device = device;
	}

	public void setDoNotDisturbOn(final boolean doNotDisturb) {
		doNotDisturbOn = doNotDisturb;
	}
}
