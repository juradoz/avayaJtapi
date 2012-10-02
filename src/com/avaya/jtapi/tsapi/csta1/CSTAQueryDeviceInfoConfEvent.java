package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTAQueryDeviceInfoConfEvent extends CSTAConfirmation {
	String device;
	short deviceType;
	int deviceClass;
	public static final int PDU = 38;

	public static CSTAQueryDeviceInfoConfEvent decode(InputStream in) {
		CSTAQueryDeviceInfoConfEvent _this = new CSTAQueryDeviceInfoConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void encodeMembers(OutputStream memberStream) {
		DeviceID.encode(this.device, memberStream);
		DeviceType.encode(this.deviceType, memberStream);
		DeviceClass.encode(this.deviceClass, memberStream);
	}

	public void decodeMembers(InputStream memberStream) {
		this.device = DeviceID.decode(memberStream);
		this.deviceType = DeviceType.decode(memberStream);
		this.deviceClass = DeviceClass.decode(memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();
		lines.add("CSTAQueryDeviceInfoConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(DeviceID.print(this.device, "device", indent));
		lines.addAll(DeviceType.print(this.deviceType, "deviceType", indent));
		lines.addAll(DeviceClass.print(this.deviceClass, "deviceClass", indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 38;
	}

	public String getDevice() {
		return this.device;
	}

	public int getDeviceClass() {
		return this.deviceClass;
	}

	public short getDeviceType() {
		return this.deviceType;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setDeviceClass(int deviceClass) {
		this.deviceClass = deviceClass;
	}

	public void setDeviceType(short deviceType) {
		this.deviceType = deviceType;
	}
}