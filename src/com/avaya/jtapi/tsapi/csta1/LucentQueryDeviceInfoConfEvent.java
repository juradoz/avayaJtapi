package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentQueryDeviceInfoConfEvent extends LucentPrivateData {
	short extensionClass;
	static final int PDU = 20;

	public static LucentQueryDeviceInfoConfEvent decode(InputStream in) {
		LucentQueryDeviceInfoConfEvent _this = new LucentQueryDeviceInfoConfEvent();
		_this.doDecode(in);

		return _this;
	}

	@Override
	public void decodeMembers(InputStream memberStream) {
		extensionClass = ASNEnumerated.decode(memberStream);
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(extensionClass, memberStream);
	}

	public short getExtensionClass() {
		return extensionClass;
	}

	@Override
	public int getPDU() {
		return 20;
	}

	@Override
	public Collection<String> print() {
		Collection<String> lines = new ArrayList<String>();

		lines.add("LucentQueryDeviceInfoConfEvent ::=");
		lines.add("{");

		String indent = "  ";

		lines.addAll(LucentExtensionClass.print(extensionClass,
				"extensionClass", indent));

		lines.add("}");
		return lines;
	}

	public void setExtensionClass(short extensionClass) {
		this.extensionClass = extensionClass;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceInfoConfEvent JD-Core Version:
 * 0.5.4
 */