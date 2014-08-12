package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentUserProvidedCode extends LucentPrivateData {
	short type;
	String data;

	public LucentUserProvidedCode() {
		this.type = 0;
	}

	public LucentUserProvidedCode(short _type, String _data) {
		this.type = _type;
		this.data = _data;
	}

	public static void encode(LucentUserProvidedCode _this, OutputStream out) {
		if (_this == null) {
			_this = new LucentUserProvidedCode();
		}
		_this.encode(out);
	}

	public void encodeMembers(OutputStream memberStream) {
		ProvidedCodeType.encode(this.type, memberStream);
		ASNIA5String.encode(this.data, memberStream);
	}

	public static Collection<String> print(LucentUserProvidedCode _this,
			String name, String _indent) {
		Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		lines.addAll(ProvidedCodeType.print(_this.type, "type", indent));
		lines.addAll(ASNIA5String.print(_this.data, "data", indent));

		lines.add(_indent + "}");
		return lines;
	}
}