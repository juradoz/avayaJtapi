package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;
import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class CSTAQueryDeviceInfoConfEvent extends CSTAConfirmation {
	String device;
	short deviceType;
	int deviceClass;
	public static final int PDU = 38;

	public static CSTAQueryDeviceInfoConfEvent decode(final InputStream in) {
		final CSTAQueryDeviceInfoConfEvent _this = new CSTAQueryDeviceInfoConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(final InputStream memberStream) {
		device = ASNIA5String.decode(memberStream);
		deviceType = ASNEnumerated.decode(memberStream);
		deviceClass = ASNBitString.decode(memberStream);
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNIA5String.encode(device, memberStream);
		ASNEnumerated.encode(deviceType, memberStream);
		DeviceClass.encode(deviceClass, memberStream);
	}

	public String getDevice() {
		return device;
	}

	public int getDeviceClass() {
		return deviceClass;
	}

	public short getDeviceType() {
		return deviceType;
	}

	@Override
	public int getPDU() {
		return 38;
	}

	@Override
	public Collection<String> print() {
		final Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAQueryDeviceInfoConfEvent ::=");
		lines.add("{");

		final String indent = "  ";

		lines.addAll(ASNIA5String.print(device, "device", indent));
		lines.addAll(DeviceType.print(deviceType, "deviceType", indent));
		lines.addAll(DeviceClass.print(deviceClass, "deviceClass", indent));

		lines.add("}");
		return lines;
	}

	public void setDevice(final String device) {
		this.device = device;
	}

	public void setDeviceClass(final int deviceClass) {
		this.deviceClass = deviceClass;
	}

	public void setDeviceType(final short deviceType) {
		this.deviceType = deviceType;
	}
}
