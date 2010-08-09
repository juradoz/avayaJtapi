package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class LucentV8FailedEvent extends LucentFailedEvent {
	CSTAExtendedDeviceID callingDevice_asn;
	static final int PDU = 141;

	static LucentFailedEvent decode(InputStream in) {
		LucentV8FailedEvent _this = new LucentV8FailedEvent();
		_this.doDecode(in);

		return _this;
	}

	public LucentV8FailedEvent() {
		callingDevice_asn = null;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		callingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNSequence.encode(callingDevice_asn, memberStream);
	}

	public CSTAExtendedDeviceID getCallingDevice() {
		return callingDevice_asn;
	}

	@Override
	public int getPDU() {
		return 141;
	}

	@Override
	public Collection<String> print() {
		Collection lines = new ArrayList();
		lines.add("LucentV8FailedEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(CSTAExtendedDeviceID.print(callingDevice_asn,
				"callingDevice", indent));
		lines.addAll(CSTADeviceHistoryData.print(deviceHistory,
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public void setCallingDevice(CSTAExtendedDeviceID device) {
		callingDevice_asn = device;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV8FailedEvent JD-Core Version: 0.5.4
 */