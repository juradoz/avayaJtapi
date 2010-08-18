package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class ConnectionList extends ASNSequenceOf {
	public static CSTAConnection[] decode(final InputStream in) {
		final ConnectionList _this = new ConnectionList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(final CSTAConnection[] array,
			final OutputStream out) {
		final ConnectionList _this = new ConnectionList(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(final CSTAConnection[] array,
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
			lines.addAll(CSTAConnection.print(array[i], null, indent));
		lines.add(_indent + "}");
		return lines;
	}

	CSTAConnection[] array;

	public ConnectionList() {
	}

	public ConnectionList(final CSTAConnection[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return CSTAConnection.decode(memberStream);
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new CSTAConnection[vec.size()];

		for (int i = 0; i < array.length; ++i)
			array[i] = (CSTAConnection) vec.elementAt(i);
	}

	@Override
	public void encodeMember(final int index, final OutputStream memberStream) {
		ASNSequence.encode(array[index], memberStream);
	}
}
