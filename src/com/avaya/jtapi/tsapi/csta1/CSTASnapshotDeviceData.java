package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public final class CSTASnapshotDeviceData extends ASNSequenceOf {
	CSTASnapshotDeviceResponseInfo[] array;

	public CSTASnapshotDeviceData() {
	}

	public CSTASnapshotDeviceData(CSTASnapshotDeviceResponseInfo[] _array) {
		this.array = _array;
	}

	public static void encode(CSTASnapshotDeviceResponseInfo[] array,
			OutputStream out) {
		CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData(array);
		_this.doEncode(array.length, out);
	}

	public void encodeMember(int index, OutputStream memberStream) {
		CSTASnapshotDeviceResponseInfo.encode(this.array[index], memberStream);
	}

	public static CSTASnapshotDeviceResponseInfo[] decode(InputStream in) {
		CSTASnapshotDeviceData _this = new CSTASnapshotDeviceData();
		_this.doDecode(in);
		return _this.array;
	}

	public void doDecode(InputStream in) {
		super.doDecode(in);

		this.array = new CSTASnapshotDeviceResponseInfo[this.vec.size()];

		for (int i = 0; i < this.array.length; i++) {
			this.array[i] = ((CSTASnapshotDeviceResponseInfo) this.vec
					.elementAt(i));
		}
	}

	public Object decodeMember(InputStream memberStream) {
		return CSTASnapshotDeviceResponseInfo.decode(memberStream);
	}

	public static Collection<String> print(
			CSTASnapshotDeviceResponseInfo[] array, String name, String _indent) {
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
			lines.addAll(CSTASnapshotDeviceResponseInfo.print(array[i], null,
					indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	public CSTASnapshotDeviceResponseInfo[] getArray() {
		return this.array;
	}
}