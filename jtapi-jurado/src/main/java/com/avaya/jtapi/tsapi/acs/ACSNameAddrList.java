package com.avaya.jtapi.tsapi.acs;

import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class ACSNameAddrList extends ASNSequenceOf {
	ACSNameAddr[] array;

	ACSNameAddrList() {
	}

	ACSNameAddrList(ACSNameAddr[] _array) {
		this.array = _array;
	}

	static ACSNameAddr[] decode(InputStream in) {
		ACSNameAddrList _this = new ACSNameAddrList();
		_this.doDecode(in);
		return _this.array;
	}

	public void doDecode(InputStream in) {
		super.doDecode(in);

		this.array = new ACSNameAddr[this.vec.size()];

		for (int i = 0; i < this.array.length; i++) {
			this.array[i] = ((ACSNameAddr) this.vec.elementAt(i));
		}
	}

	public Object decodeMember(InputStream memberStream) {
		return ACSNameAddr.decode(memberStream);
	}

	public static void encode(ACSNameAddr[] array, OutputStream out) {
		ACSNameAddrList _this = new ACSNameAddrList(array);
		_this.doEncode(array.length, out);
	}

	public void encodeMember(int index, OutputStream memberStream) {
		ACSNameAddr.encode(this.array[index], memberStream);
	}

	static Collection<String> print(ACSNameAddr[] array, String name,
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

		for (int i = 0; i < array.length; i++) {
			lines.addAll(ACSNameAddr.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}
}