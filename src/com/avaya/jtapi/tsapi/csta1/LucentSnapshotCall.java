package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class LucentSnapshotCall extends ASNSequenceOf {
	public static CSTASnapshotCallResponseInfo[] decode(final InputStream in) {
		final LucentSnapshotCall _this = new LucentSnapshotCall();
		_this.doDecode(in);
		return _this.array;
	}

	static void encode(final CSTASnapshotCallResponseInfo[] array,
			final OutputStream out) {
		final LucentSnapshotCall _this = new LucentSnapshotCall(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(
			final CSTASnapshotCallResponseInfo[] array, final String name,
			final String _indent) {
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
			lines.addAll(CSTASnapshotCallResponseInfo.print(array[i], null,
					indent));
		lines.add(_indent + "}");
		return lines;
	}

	CSTASnapshotCallResponseInfo[] array;

	public LucentSnapshotCall() {
	}

	public LucentSnapshotCall(final CSTASnapshotCallResponseInfo[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return CSTASnapshotCallResponseInfo.decode(memberStream);
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new CSTASnapshotCallResponseInfo[vec.size()];

		for (int i = 0; i < array.length; ++i)
			array[i] = (CSTASnapshotCallResponseInfo) vec.elementAt(i);
	}

	@Override
	public void encodeMember(final int index, final OutputStream memberStream) {
		ASNSequence.encode(array[index], memberStream);
	}
}
