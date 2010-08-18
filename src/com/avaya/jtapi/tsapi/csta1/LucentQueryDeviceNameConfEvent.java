package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public class LucentQueryDeviceNameConfEvent extends LucentPrivateData {
	short deviceType;
	String device_asn;
	String name;
	static final int PDU = 50;

	public static LucentQueryDeviceNameConfEvent decode(final InputStream in) {
		final LucentQueryDeviceNameConfEvent _this = new LucentQueryDeviceNameConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		deviceType = ASNEnumerated.decode(memberStream);
		device_asn = ASNIA5String.decode(memberStream);
		name = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(deviceType, memberStream);
		ASNIA5String.encode(device_asn, memberStream);
		ASNIA5String.encode(name, memberStream);
	}

	public String getDevice_asn() {
		return device_asn;
	}

	public short getDeviceType() {
		return deviceType;
	}

	public String getName() {
		return name;
	}

	@Override
	public int getPDU() {
		return 50;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryDeviceNameConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(LucentDeviceType.print(deviceType, "deviceType", indent));
		lines.addAll(ASNIA5String.print(device_asn, "device", indent));
		lines.addAll(ASNIA5String.print(name, "name", indent));

		lines.add("}");
		return lines;
	}

	public void setDevice_asn(final String device_asn) {
		this.device_asn = device_asn;
	}

	public void setDeviceType(final short deviceType) {
		this.deviceType = deviceType;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
