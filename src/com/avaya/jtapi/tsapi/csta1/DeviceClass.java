package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class DeviceClass extends ASNBitString {
	static final int DC_VOICE = -2147483648;
	static final int DC_DATA = 1073741824;
	static final int DC_IMAGE = 536870912;
	static final int DC_OTHER = 268435456;
	static final int numBits = 4;

	static void encode(final int bits, final OutputStream out) {
		ASNBitString.encode(bits, 4, out);
	}

	static Collection<String> print(final int bits, final String name,
			final String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0)
			str = str + "DC_VOICE ";
		if ((bits & 0x40000000) != 0)
			str = str + "DC_DATA ";
		if ((bits & 0x20000000) != 0)
			str = str + "DC_IMAGE ";
		if ((bits & 0x10000000) != 0)
			str = str + "DC_OTHER ";

		return ASNBitString.print(bits, str, name, indent);
	}
}
