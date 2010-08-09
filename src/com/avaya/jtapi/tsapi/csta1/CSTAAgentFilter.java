package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class CSTAAgentFilter extends ASNBitString {
	static final int AF_LOGGED_ON = -2147483648;
	static final int AF_LOGGED_OFF = 1073741824;
	static final int AF_NOT_READY = 536870912;
	static final int AF_READY = 268435456;
	static final int AF_WORK_NOT_READY = 134217728;
	static final int AF_WORK_READY = 67108864;
	static final int numBits = 6;

	static void encode(int bits, OutputStream out) {
		encode(bits, 6, out);
	}

	static Collection<String> print(int bits, String name, String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0) {
			str = str + "AF_LOGGED_ON ";
		}
		if ((bits & 0x40000000) != 0) {
			str = str + "AF_LOGGED_OFF ";
		}
		if ((bits & 0x20000000) != 0) {
			str = str + "AF_NOT_READY ";
		}
		if ((bits & 0x10000000) != 0) {
			str = str + "AF_READY ";
		}
		if ((bits & 0x8000000) != 0) {
			str = str + "AF_WORK_NOT_READY ";
		}
		if ((bits & 0x4000000) != 0) {
			str = str + "AF_WORK_READY ";
		}

		return print(bits, str, name, indent);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.CSTAAgentFilter JD-Core Version: 0.5.4
 */