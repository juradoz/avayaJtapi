package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;
import java.io.OutputStream;
import java.util.Collection;

public final class DeviceClass extends ASNBitString {
	static final int DC_VOICE = -2147483648;
	static final int DC_DATA = 1073741824;
	static final int DC_IMAGE = 536870912;
	static final int DC_OTHER = 268435456;
	static final int numBits = 4;

	static void encode(int bits, OutputStream out) {
		encode(bits, 4, out);
	}

	static Collection<String> print(int bits, String name, String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0) {
			str = str + "DC_VOICE ";
		}
		if ((bits & 0x40000000) != 0) {
			str = str + "DC_DATA ";
		}
		if ((bits & 0x20000000) != 0) {
			str = str + "DC_IMAGE ";
		}
		if ((bits & 0x10000000) != 0) {
			str = str + "DC_OTHER ";
		}

		return print(bits, str, name, indent);
	}
}