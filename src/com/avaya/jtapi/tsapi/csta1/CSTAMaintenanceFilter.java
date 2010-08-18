package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class CSTAMaintenanceFilter extends ASNBitString {
	static final int MF_BACK_IN_SERVICE = -2147483648;
	static final int MF_OUT_OF_SERVICE = 1073741824;
	static final int numBits = 2;

	static void encode(final int bits, final OutputStream out) {
		ASNBitString.encode(bits, 2, out);
	}

	static Collection<String> print(final int bits, final String name,
			final String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0)
			str = str + "MF_BACK_IN_SERVICE ";
		if ((bits & 0x40000000) != 0)
			str = str + "MF_OUT_OF_SERVICE ";

		return ASNBitString.print(bits, str, name, indent);
	}
}
