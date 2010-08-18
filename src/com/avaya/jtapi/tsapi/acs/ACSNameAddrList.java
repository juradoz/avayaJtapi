package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class ACSNameAddrList extends ASNSequenceOf {
	static ACSNameAddr[] decode(final InputStream in) {
		final ACSNameAddrList _this = new ACSNameAddrList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(final ACSNameAddr[] array, final OutputStream out) {
		final ACSNameAddrList _this = new ACSNameAddrList(array);
		_this.doEncode(array.length, out);
	}

	static Collection<String> print(final ACSNameAddr[] array,
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
			lines.addAll(ACSNameAddr.print(array[i], null, indent));
		lines.add(_indent + "}");
		return lines;
	}

	ACSNameAddr[] array;

	ACSNameAddrList() {
	}

	ACSNameAddrList(final ACSNameAddr[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return ACSNameAddr.decode(memberStream);
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new ACSNameAddr[vec.size()];

		for (int i = 0; i < array.length; ++i)
			array[i] = (ACSNameAddr) vec.elementAt(i);
	}

	@Override
	public void encodeMember(final int index, final OutputStream memberStream) {
		ASNSequence.encode(array[index], memberStream);
	}
}
