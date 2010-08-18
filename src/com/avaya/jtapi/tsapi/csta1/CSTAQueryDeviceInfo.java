package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAQueryDeviceInfo extends CSTARequest {
	String device;
	public static final int PDU = 37;

	public static CSTAQueryDeviceInfo decode(final InputStream in) {
		final CSTAQueryDeviceInfo _this = new CSTAQueryDeviceInfo();
		_this.doDecode(in);

		return _this;
	}

	public CSTAQueryDeviceInfo() {
	}

	public CSTAQueryDeviceInfo(final String _device) {
		device = _device;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
	}

	public String getDevice() {
		return device;
	}

	@Override
	public int getPDU() {
		return 37;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAQueryDeviceInfo ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));

		lines.add("}");
		return lines;
	}

	public void setDevice(final String device) {
		this.device = device;
	}
}
