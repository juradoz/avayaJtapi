package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class ConnectionList extends ASNSequenceOf {
	public static CSTAConnection[] decode(InputStream in) {
		ConnectionList _this = new ConnectionList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(CSTAConnection[] array, OutputStream out) {
		ConnectionList _this = new ConnectionList(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(CSTAConnection[] array, String name,
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
			lines.addAll(CSTAConnection.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	CSTAConnection[] array;

	public ConnectionList() {
	}

	public ConnectionList(CSTAConnection[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return CSTAConnection.decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new CSTAConnection[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((CSTAConnection) vec.elementAt(i));
		}
	}

	@Override
	public void encodeMember(int index, OutputStream memberStream) {
		ASNSequence.encode(array[index], memberStream);
	}
}
