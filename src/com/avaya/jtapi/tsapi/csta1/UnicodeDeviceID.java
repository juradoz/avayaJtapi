package com.avaya.jtapi.tsapi.csta1;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNIA5String;
import com.avaya.jtapi.tsapi.asn1.ASNInteger;
import com.avaya.jtapi.tsapi.asn1.ASNSequenceOf;

public final class UnicodeDeviceID extends ASNSequenceOf {
	public static String decode(final InputStream in) {
		final UnicodeDeviceID _this = new UnicodeDeviceID();
		_this.doDecode(in);
		if (_this.array.length > 0)
			return new String(_this.array);
		return null;
	}

	public static void encode(final String device,
			final OutputStream memberStream) {
		final char[] deviceArray = device.toCharArray();
		final UnicodeDeviceID _this = new UnicodeDeviceID(deviceArray);
		_this.doEncode(deviceArray.length, memberStream);
	}

	public static Collection<String> print(final String str, final String name,
			final String indent) {
		return ASNIA5String.print(str, name, indent);
	}

	char[] array;

	public UnicodeDeviceID() {
	}

	public UnicodeDeviceID(final char[] _array) {
		array = _array;
	}

	@Override
	public Object decodeMember(final InputStream memberStream) {
		return new Character((char) ASNInteger.decode(memberStream));
	}

	@Override
	public void doDecode(final InputStream in) {
		super.doDecode(in);

		array = new char[vec.size()];

		for (int i = 0; i < array.length; ++i)
			array[i] = ((Character) vec.elementAt(i)).charValue();
	}

	@Override
	public void encodeMember(final int idx, final OutputStream memberStream) {
		ASNInteger.encode(array[idx], memberStream);
	}
}
