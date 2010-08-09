package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequence;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class CSTASnapshotDeviceData extends ASNSequenceOf {
	public static CSTASnapshotDeviceResponseInfo[] decode(InputStream in) {
		CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData();
		_this.doDecode(in);
		return _this.array;
	}

	public static void encode(CSTASnapshotDeviceResponseInfo[] array,
			OutputStream out) {
		CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(
			CSTASnapshotDeviceResponseInfo[] array, String name, String _indent) {
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
			lines.addAll(CSTASnapshotDeviceResponseInfo.print(array[i], null,
					indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	CSTASnapshotDeviceResponseInfo[] array;

	public CSTASnapshotDeviceData() {
	}

	public CSTASnapshotDeviceData(CSTASnapshotDeviceResponseInfo[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return CSTASnapshotDeviceResponseInfo.decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new CSTASnapshotDeviceResponseInfo[vec.size()];

		for (int i = 0; i < array.length; ++i) {
			array[i] = ((CSTASnapshotDeviceResponseInfo) vec.elementAt(i));
		}
	}

	@Override
	public void encodeMember(int index, OutputStream memberStream) {
		ASNSequence.encode(array[index], memberStream);
	}

	public CSTASnapshotDeviceResponseInfo[] getArray() {
		return array;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTASnapshotDeviceData JD-Core Version: 0.5.4
 */