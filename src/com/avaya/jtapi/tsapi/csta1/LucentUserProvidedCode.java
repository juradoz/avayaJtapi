package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNIA5String;

public final class LucentUserProvidedCode extends LucentPrivateData {
	public static void encode(LucentUserProvidedCode _this,
			final OutputStream out) {
		if (_this == null)
			_this = new LucentUserProvidedCode();
		_this.encode(out);
	}

	public static Collection<String> print(final LucentUserProvidedCode _this,
			final String name, final String _indent) {
		final Collection<String> lines = new ArrayList<String>();

		if (_this == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null)
			lines.add(_indent + name);
		lines.add(_indent + "{");

		final String indent = _indent + "  ";

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

	public LucentUserProvidedCode(final short _type, final String _data) {
		type = _type;
		data = _data;
	}

	@Override
	public void encodeMembers(final OutputStream memberStream) {
		ASNEnumerated.encode(type, memberStream);
		ASNIA5String.encode(data, memberStream);
	}
}
