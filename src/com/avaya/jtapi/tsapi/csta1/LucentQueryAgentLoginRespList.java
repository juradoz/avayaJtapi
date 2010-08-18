package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class LucentQueryAgentLoginRespList extends ASNSequenceOf {
	public static String[] decode(InputStream in) {
		LucentQueryAgentLoginRespList _this = new LucentQueryAgentLoginRespList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(String[] array, OutputStream out) {
		LucentQueryAgentLoginRespList _this = new LucentQueryAgentLoginRespList(
				array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(String[] array, String name,
			String _indent) {
		Collection<String> lines = new ArrayList<String>();

		if (array == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null) {
			lines.add(_indent + name);
		}
		lines.add(_indent + "{");

		String indent = _indent + "  ";

		for (int i = 0; i < array.length; ++i) {
			lines.addAll(ASNIA5String.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	String[] array;

	public LucentQueryAgentLoginRespList() {
	}

	public LucentQueryAgentLoginRespList(String[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return ASNIA5String.decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new String[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((String) vec.elementAt(i));
		}
	}

	@Override
	public void encodeMember(int index, OutputStream memberStream) {
		ASNIA5String.encode(array[index], memberStream);
	}
}
