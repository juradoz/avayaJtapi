package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class ListForwardParameters extends ASNSequenceOf {
	public static CSTAForwardingInfo[] decode(InputStream in) {
		ListForwardParameters _this = new ListForwardParameters();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(CSTAForwardingInfo[] array, OutputStream out) {
		ListForwardParameters _this = new ListForwardParameters(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(CSTAForwardingInfo[] array,
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
			lines.addAll(CSTAForwardingInfo.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	CSTAForwardingInfo[] array;

	public ListForwardParameters() {
	}

	public ListForwardParameters(CSTAForwardingInfo[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return CSTAForwardingInfo.decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new CSTAForwardingInfo[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((CSTAForwardingInfo) vec.elementAt(i));
		}
	}

	@Override
	public void encodeMember(int index, OutputStream memberStream) {
		ASNSequence.encode(array[index], memberStream);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.ListForwardParameters JD-Core Version: 0.5.4
 */