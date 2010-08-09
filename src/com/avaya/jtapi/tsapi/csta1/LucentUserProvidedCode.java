package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentUserProvidedCode extends LucentPrivateData {
	public static void encode(LucentUserProvidedCode _this, OutputStream out) {
		if (_this == null) {
			_this = new LucentUserProvidedCode();
		}
		_this.encode(out);
	}

	public static Collection<String> print(LucentUserProvidedCode _this,
			String name, String _indent) {
		Collection lines = new ArrayList();

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

	short type;

	String data;

	public LucentUserProvidedCode() {
		type = 0;
	}

	public LucentUserProvidedCode(short _type, String _data) {
		type = _type;
		data = _data;
	}

	@Override
	public void encodeMembers(OutputStream memberStream) {
		ASNEnumerated.encode(type, memberStream);
		ASNIA5String.encode(data, memberStream);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentUserProvidedCode JD-Core Version: 0.5.4
 */