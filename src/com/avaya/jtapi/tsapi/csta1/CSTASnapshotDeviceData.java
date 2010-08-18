package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class CSTASnapshotDeviceData extends ASNSequenceOf {
	public static CSTASnapshotDeviceResponseInfo[] decode(final InputStream in) {
		final CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(final CSTASnapshotDeviceResponseInfo[] array,
			final OutputStream out) {
		final CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(
			final CSTASnapshotDeviceResponseInfo[] array, final String name,
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
			lines.addAll(CSTASnapshotDeviceResponseInfo.print(array[i], null,
					indent));
		lines.add(_indent + "}");
		return lines;
	}

	CSTASnapshotDeviceResponseInfo[] array;

	public CSTASnapshotDeviceData() {
	}

	public CSTASnapshotDeviceData(final CSTASnapshotDeviceResponseInfo[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return CSTASnapshotDeviceResponseInfo.decode(memberStream);
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new CSTASnapshotDeviceResponseInfo[vec.size()];

		for (int i = 0; i < array.length; ++i)
			array[i] = (CSTASnapshotDeviceResponseInfo) vec.elementAt(i);
	}

	@Override
	public void encodeMember(final int index, final OutputStream memberStream) {
		ASNSequence.encode(array[index], memberStream);
	}

	public CSTASnapshotDeviceResponseInfo[] getArray() {
		return array;
	}
}
