package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class LucentPrivateFilter extends ASNBitString {
	static final int ATT_ENTERED_DIGITS_FILTER = -2147483648;
	static final int ATT_CHARGE_ADVICE_FILTER = 1073741824;
	static final int numBits = 2;

	static void encode(int bits, OutputStream out) {
		encode(bits, 2, out);
	}

	static Collection<String> print(int bits, String name, String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0) {
			str = str + "ATT_ENTERED_DIGITS_FILTER ";
		}
		if ((bits & 0x40000000) != 0) {
			str = str + "ATT_CHARGE_ADVICE_FILTER ";
		}

		return print(bits, str, name, indent);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.LucentPrivateFilter JD-Core Version: 0.5.4
 */