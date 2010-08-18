package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class CSTAForwardingEvent extends CSTAUnsolicited {
	CSTAExtendedDeviceID device;
	CSTAForwardingInfo forwardingInformation;
	public static final int PDU = 70;

	public static CSTAForwardingEvent decode(InputStream in) {
		CSTAForwardingEvent _this = new CSTAForwardingEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		device = CSTAExtendedDeviceID.decode(memberStream);
		forwardingInformation = CSTAForwardingInfo.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNSequence.encode(device, memberStream);
		ASNSequence.encode(forwardingInformation, memberStream);
	}

	public CSTAExtendedDeviceID getDevice() {
		return device;
	}

	public CSTAForwardingInfo getForwardingInformation() {
		return forwardingInformation;
	}

	@Override
	public int getPDU() {
		return 70;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAForwardingEvent ::=");
		lines.add("{");

		String indent = "  ";
		lines.add(indent + "monitorCrossRefID " + monitorCrossRefID);
		lines.addAll(CSTAExtendedDeviceID.print(device, "device", indent));
		lines.addAll(CSTAForwardingInfo.print(forwardingInformation,
				"forwardingInformation", indent));

		lines.add("}");
		return lines;
	}

	public void setDevice(CSTAExtendedDeviceID device) {
		this.device = device;
	}

	public void setForwardingInformation(CSTAForwardingInfo fwdInfo) {
		forwardingInformation = fwdInfo;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAForwardingEvent JD-Core Version: 0.5.4
 */