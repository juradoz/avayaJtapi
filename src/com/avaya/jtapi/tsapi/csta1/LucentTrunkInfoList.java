package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class LucentTrunkInfoList extends ASNSequenceOf {
	public static CSTATrunkInfo[] decode(final InputStream in) {
		final LucentTrunkInfoList _this = new LucentTrunkInfoList();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(final CSTATrunkInfo[] _array,
			final OutputStream out) {
		final LucentTrunkInfoList _this = new LucentTrunkInfoList(_array);
		_this.doEncode(_array.length, out);
	}

	public static Collection<String> print(final CSTATrunkInfo[] array,
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
			lines.addAll(CSTATrunkInfo.print(array[i], null, indent));
		lines.add(_indent + "}");
		return lines;
	}

	CSTATrunkInfo[] array;

	public LucentTrunkInfoList() {
	}

	public LucentTrunkInfoList(final CSTATrunkInfo[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return CSTATrunkInfo.decode(memberStream);
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new CSTATrunkInfo[vec.size()];

		for (int i = 0; i < array.length; ++i)
			array[i] = (CSTATrunkInfo) vec.elementAt(i);
	}

	@Override
	public void encodeMember(final int idx, final OutputStream memberStream) {
		ASNSequence.encode(array[idx], memberStream);
	}
}
