package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

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

	@Override
	public void decodeMembers(InputStream memberStream) {
		super.decodeMembers(memberStream);
		associatedClass = ASNEnumerated.decode(memberStream);
		associatedDevice = ASNIA5String.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		super.encodeMembers(memberStream);
		ASNEnumerated.encode(associatedClass, memberStream);
		ASNIA5String.encode(associatedDevice, memberStream);
	}

	public short getAssociatedClass() {
		return associatedClass;
	}

	public String getAssociatedDevice() {
		return associatedDevice;
	}

	@Override
	public int getPDU() {
		return 98;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentV5QueryDeviceInfoConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentExtensionClass.print(extensionClass,
				"extensionClass", indent));
		lines.addAll(LucentExtensionClass.print(associatedClass,
				"associatedClass", indent));
		lines.addAll(ASNIA5String.print(associatedDevice, "associatedDevice",
				indent));

		lines.add("}");
		return lines;
	}

	public void setAssociatedClass(short associatedClass) {
		this.associatedClass = associatedClass;
	}

	public void setAssociatedDevice(String associatedDevice) {
		this.associatedDevice = associatedDevice;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent JD-Core Version:
 * 0.5.4
 */