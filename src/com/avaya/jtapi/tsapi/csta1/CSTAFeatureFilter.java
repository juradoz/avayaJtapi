package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class CSTAFeatureFilter extends ASNBitString {
	static final int FF_CALL_INFORMATION = -2147483648;
	static final int FF_DO_NOT_DISTURB = 1073741824;
	static final int FF_FORWARDING = 536870912;
	static final int FF_MESSAGE_WAITING = 268435456;
	static final int numBits = 4;

	static void encode(final int bits, final OutputStream out) {
		ASNBitString.encode(bits, 4, out);
	}

	static Collection<String> print(final int bits, final String name,
			final String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0)
			str = str + "FF_CALL_INFORMATION ";
		if ((bits & 0x40000000) != 0)
			str = str + "FF_DO_NOT_DISTURB ";
		if ((bits & 0x20000000) != 0)
			str = str + "FF_FORWARDING ";
		if ((bits & 0x10000000) != 0)
			str = str + "FF_MESSAGE_WAITING ";

		return ASNBitString.print(bits, str, name, indent);
	}
}
