package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;

public final class LucentV8FailedEvent extends LucentFailedEvent {
	CSTAExtendedDeviceID callingDevice_asn;
	static final int PDU = 141;

	static LucentFailedEvent decode(final InputStream in) {
		final LucentV8FailedEvent _this = new LucentV8FailedEvent();
		_this.doDecode(in);

		return _this;
	}

	public LucentV8FailedEvent() {
		callingDevice_asn = null;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		super.decodeMembers(memberStream);
		callingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
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
		final Collection<String> lines = new ArrayList<String>();
		lines.add("LucentV8FailedEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(CSTAExtendedDeviceID.print(callingDevice_asn,
				"callingDevice", indent));
		lines.addAll(CSTADeviceHistoryData.print(deviceHistory,
				"deviceHistory", indent));

		lines.add("}");
		return lines;
	}

	public void setCallingDevice(final CSTAExtendedDeviceID device) {
		callingDevice_asn = device;
	}
}
