package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class LucentConnIDList extends ASNSequenceOf {
	CSTAConnectionID[] array;

	public LucentConnIDList() {
	}

	public LucentConnIDList(CSTAConnectionID[] _array) {
		this.array = _array;
	}

	public static CSTAConnectionID[] decode(InputStream in) {
		LucentConnIDList _this = new LucentConnIDList();
		_this.doDecode(in);
		return _this.array;
	}

	public void doDecode(InputStream in) {
		super.doDecode(in);

		this.array = new CSTAConnectionID[this.vec.size()];

		for (int i = 0; i < this.array.length; i++) {
			this.array[i] = ((CSTAConnectionID) this.vec.elementAt(i));
		}
	}

	public Object decodeMember(InputStream memberStream) {
		return decode(memberStream);
	}

	public static void encode(CSTAConnectionID[] array, OutputStream out) {
		LucentConnIDList _this = new LucentConnIDList(array);
		_this.doEncode(array == null ? 0 : array.length, out);
	}

	public void encodeMember(int index, OutputStream memberStream) {
		CSTAConnectionID.encode(this.array[index], memberStream);
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

		for (int i = 0; i < array.length; i++) {
			lines.addAll(CSTAConnectionID.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}
}