package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class LucentConnIDList extends ASNSequenceOf {
	public static CSTAConnectionID[] decode(final InputStream in) {
		final LucentConnIDList _this = new LucentConnIDList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(final CSTAConnectionID[] array,
			final OutputStream out) {
		final LucentConnIDList _this = new LucentConnIDList(array);
		_this.doEncode(array == null ? 0 : array.length, out);
	}

	public static Collection<String> print(final CSTAConnectionID[] array,
			final String name, final String _indent) {
		final Collection<String> lines = new ArrayList<String>();

		if (array == null) {
			lines.add(_indent + name + " <null>");
			return lines;
		}
		if (name != null)
			lines.add(_indent + name);
		lines.add(_indent + "{");

		final String indent = _indent + "  ";

		for (int i = 0; i < array.length; ++i)
			lines.addAll(CSTAConnectionID.print(array[i], null, indent));
		lines.add(_indent + "}");
		return lines;
	}

	CSTAConnectionID[] array;

	public LucentConnIDList() {
	}

	public LucentConnIDList(final CSTAConnectionID[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return LucentConnIDList.decode(memberStream);
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new CSTAConnectionID[vec.size()];

		for (int i = 0; i < array.length; ++i)
			array[i] = (CSTAConnectionID) vec.elementAt(i);
	}

	@Override
	public void encodeMember(final int index, final OutputStream memberStream) {
		CSTAConnectionID.encode(array[index], memberStream);
	}
}
