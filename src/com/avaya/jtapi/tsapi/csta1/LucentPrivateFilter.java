package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class LucentPrivateFilter extends ASNBitString {
	static final int ATT_ENTERED_DIGITS_FILTER = -2147483648;
	static final int ATT_CHARGE_ADVICE_FILTER = 1073741824;
	static final int numBits = 2;

	static void encode(final int bits, final OutputStream out) {
		ASNBitString.encode(bits, 2, out);
	}

	static Collection<String> print(final int bits, final String name,
			final String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0)
			str = str + "ATT_ENTERED_DIGITS_FILTER ";
		if ((bits & 0x40000000) != 0)
			str = str + "ATT_CHARGE_ADVICE_FILTER ";

		return ASNBitString.print(bits, str, name, indent);
	}
}
