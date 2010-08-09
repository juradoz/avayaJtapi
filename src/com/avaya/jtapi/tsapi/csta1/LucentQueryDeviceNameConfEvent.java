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

	public static LucentQueryDeviceNameConfEvent decode(InputStream in) {
		LucentQueryDeviceNameConfEvent _this = new LucentQueryDeviceNameConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		deviceType = ASNEnumerated.decode(memberStream);
		device_asn = ASNIA5String.decode(memberStream);
		name = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
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
		Collection lines = new ArrayList();

		lines.add("LucentQueryDeviceNameConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentDeviceType.print(deviceType, "deviceType", indent));
		lines.addAll(ASNIA5String.print(device_asn, "device", indent));
		lines.addAll(ASNIA5String.print(name, "name", indent));

		lines.add("}");
		return lines;
	}

	public void setDevice_asn(String device_asn) {
		this.device_asn = device_asn;
	}

	public void setDeviceType(short deviceType) {
		this.deviceType = deviceType;
	}

	public void setName(String name) {
		this.name = name;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceNameConfEvent JD-Core Version:
 * 0.5.4
 */