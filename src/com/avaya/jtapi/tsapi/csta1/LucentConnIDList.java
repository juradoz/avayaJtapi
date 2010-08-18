package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class LucentConnIDList extends ASNSequenceOf {
	public static CSTAConnectionID[] decode(InputStream in) {
		LucentConnIDList _this = new LucentConnIDList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(CSTAConnectionID[] array, OutputStream out) {
		LucentConnIDList _this = new LucentConnIDList(array);
		_this.doEncode((array == null) ? 0 : array.length, out);
	}

	public static Collection<String> print(CSTAConnectionID[] array,
			String name, String _indent) {
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
			lines.addAll(CSTAConnectionID.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	CSTAConnectionID[] array;

	public LucentConnIDList() {
	}

	public LucentConnIDList(CSTAConnectionID[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new CSTAConnectionID[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((CSTAConnectionID) vec.elementAt(i));
		}
	}

	@Override
	public void encodeMember(int index, OutputStream memberStream) {
		CSTAConnectionID.encode(array[index], memberStream);
	}
}

