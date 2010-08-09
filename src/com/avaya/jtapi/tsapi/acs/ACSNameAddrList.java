package com.avaya.jtapi.tsapi.acs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class ACSNameAddrList extends ASNSequenceOf {
	static ACSNameAddr[] decode(InputStream in) {
		ACSNameAddrList _this = new ACSNameAddrList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(ACSNameAddr[] array, OutputStream out) {
		ACSNameAddrList _this = new ACSNameAddrList(array);
		_this.doEncode(array.length, out);
	}

	static Collection<String> print(ACSNameAddr[] array, String name,
			String _indent) {
		Collection lines = new ArrayList();
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
			lines.addAll(ACSNameAddr.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	ACSNameAddr[] array;

	ACSNameAddrList() {
	}

	ACSNameAddrList(ACSNameAddr[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return ACSNameAddr.decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new ACSNameAddr[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((ACSNameAddr) vec.elementAt(i));
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
 * com.avaya.jtapi.tsapi.acs.ACSNameAddrList JD-Core Version: 0.5.4
 */