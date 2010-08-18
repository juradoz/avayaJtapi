package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class CSTACallState extends ASNSequenceOf {
	public static short[] decode(InputStream in) {
		CSTACallState _this = new CSTACallState();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(short[] array, OutputStream out) {
		CSTACallState _this = new CSTACallState(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(short[] array, String name,
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
			lines.addAll(LocalConnectionState.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	short[] array;

	public CSTACallState() {
	}

	public CSTACallState(short[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return new Integer(ASNEnumerated.decode(memberStream));
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new short[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = (short) ((Integer) vec.elementAt(i)).intValue();
		}
	}

	@Override
	public void encodeMember(int index, OutputStream memberStream) {
		ASNEnumerated.encode(array[index], memberStream);
	}

	public short[] getArray() {
		return array;
	}
}

