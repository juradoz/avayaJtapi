package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentV5QueryDeviceInfoConfEvent extends
		LucentQueryDeviceInfoConfEvent {
	short associatedClass;
	String associatedDevice;
	static final int PDU = 98;

	public static LucentQueryDeviceInfoConfEvent decode(InputStream in) {
		LucentV5QueryDeviceInfoConfEvent _this = new LucentV5QueryDeviceInfoConfEvent();
		_this.doDecode(in);

		return _this;
	}

	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		this.associatedClass = LucentExtensionClass.decode(memberStream);
		this.associatedDevice = DeviceID.decode(memberStream);
	}

	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		LucentExtensionClass.encode(this.associatedClass, memberStream);
		DeviceID.encode(this.associatedDevice, memberStream);
	}

	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5QueryDeviceInfoConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentExtensionClass.print(this.extensionClass,
				"extensionClass", indent));
		lines.addAll(LucentExtensionClass.print(this.associatedClass,
				"associatedClass", indent));
		lines.addAll(DeviceID.print(this.associatedDevice, "associatedDevice",
				indent));

		lines.add("}");
		return lines;
	}

	public int getPDU() {
		return 98;
	}

	public short getAssociatedClass() {
		return this.associatedClass;
	}

	public String getAssociatedDevice() {
		return this.associatedDevice;
	}

	public void setAssociatedClass(short associatedClass) {
		this.associatedClass = associatedClass;
	}

	public void setAssociatedDevice(String associatedDevice) {
		this.associatedDevice = associatedDevice;
	}
}