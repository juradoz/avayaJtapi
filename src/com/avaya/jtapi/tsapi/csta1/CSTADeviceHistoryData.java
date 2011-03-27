package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class CSTADeviceHistoryData extends ASNSequenceOf {
	public static LucentDeviceHistoryEntry[] decode(final InputStream in) {
		final CSTADeviceHistoryData _this = new CSTADeviceHistoryData();
		_this.doDecode(in);
		return _this.array;
	}

	static void encode(final LucentDeviceHistoryEntry[] array,
			final OutputStream out) {
		final CSTADeviceHistoryData _this = new CSTADeviceHistoryData(array);
		_this.doEncode(array.length, out);
	}

	public static Collection<String> print(
			final LucentDeviceHistoryEntry[] array, final String name,
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
			lines.addAll(LucentDeviceHistoryEntry.print(array[i], null, indent));
		lines.add(_indent + "}");
		return lines;
	}

	LucentDeviceHistoryEntry[] array;

	CSTADeviceHistoryData() {
	}

	public CSTADeviceHistoryData(final LucentDeviceHistoryEntry[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return LucentDeviceHistoryEntry.decode(memberStream);
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new LucentDeviceHistoryEntry[vec.size()];
		for (int i = 0; i < array.length; ++i)
			array[i] = (LucentDeviceHistoryEntry) vec.elementAt(i);
	}

	@Override
	public void encodeMember(final int index, final OutputStream memberStream) {
		LucentDeviceHistoryEntry.encode(array[index], memberStream);
	}

	public LucentDeviceHistoryEntry[] getArray() {
		return array;
	}
}
