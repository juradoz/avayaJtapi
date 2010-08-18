package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class LucentTrunkInfoList extends ASNSequenceOf {
	public static CSTATrunkInfo[] decode(InputStream in) {
		LucentTrunkInfoList _this = new LucentTrunkInfoList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(CSTATrunkInfo[] _array, OutputStream out) {
		LucentTrunkInfoList _this = new LucentTrunkInfoList(_array);
		_this.doEncode(_array.length, out);
	}

	public static Collection<String> print(CSTATrunkInfo[] array, String name,
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
			lines.addAll(CSTATrunkInfo.print(array[i], null, indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	CSTATrunkInfo[] array;

	public LucentTrunkInfoList() {
	}

	public LucentTrunkInfoList(CSTATrunkInfo[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return CSTATrunkInfo.decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new CSTATrunkInfo[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((CSTATrunkInfo) vec.elementAt(i));
		}
	}

	@Override
	public void encodeMember(int idx, OutputStream memberStream) {
		ASNSequence.encode(array[idx], memberStream);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentTrunkInfoList JD-Core Version: 0.5.4
 */