package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class CSTADeviceHistoryData extends ASNSequenceOf {
	public static LucentDeviceHistoryEntry[] decode(InputStream in) {
		CSTADeviceHistoryData _this = new CSTADeviceHistoryData();
		_this.doDecode(in);
		return _this.array;
	}

	static void encode(LucentDeviceHistoryEntry[] array, OutputStream out) {
		CSTADeviceHistoryData _this = new CSTADeviceHistoryData(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(LucentDeviceHistoryEntry[] array,
			String name, String _indent) {
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
			lines
					.addAll(LucentDeviceHistoryEntry.print(array[i], null,
							indent));
		}
		lines.add(_indent + "}");
		return lines;
	}

	LucentDeviceHistoryEntry[] array;

	CSTADeviceHistoryData() {
	}

	public CSTADeviceHistoryData(LucentDeviceHistoryEntry[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(InputStream memberStream) {
		return LucentDeviceHistoryEntry.decode(memberStream);
	}

	@Override
	public void doDecode(InputStream in) {
		super.doDecode(in);

		array = new LucentDeviceHistoryEntry[vec.size()];
		for (int i = 0; i < array.length; ++i) {
			array[i] = ((LucentDeviceHistoryEntry) vec.elementAt(i));
		}
	}

	@Override
	public void encodeMember(int index, OutputStream memberStream) {
		LucentDeviceHistoryEntry.encode(array[index], memberStream);
	}

	public LucentDeviceHistoryEntry[] getArray() {
		return array;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTADeviceHistoryData JD-Core Version: 0.5.4
 */