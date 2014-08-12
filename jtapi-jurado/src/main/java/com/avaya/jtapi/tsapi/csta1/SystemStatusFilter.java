package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;
import java.io.OutputStream;
import java.util.Collection;

public class SystemStatusFilter extends ASNBitString {
	public static final int SF_INITIALIZING = -2147483648;
	public static final int SF_ENABLED = 1073741824;
	public static final int SF_NORMAL = 536870912;
	public static final int SF_MESSAGES_LOST = 268435456;
	public static final int SF_DISABLED = 134217728;
	public static final int SF_OVERLOAD_IMMINENT = 67108864;
	public static final int SF_OVERLOAD_REACHED = 33554432;
	public static final int SF_OVERLOAD_RELIEVED = 16777216;
	static final int numBits = 8;

	static void encode(int bits, OutputStream out) {
		encode(bits, 8, out);
	}

	public static Collection<String> print(int bits, String name, String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0) {
			str = str + "SF_INITIALIZING ";
		}
		if ((bits & 0x40000000) != 0) {
			str = str + "SF_ENABLED ";
		}
		if ((bits & 0x20000000) != 0) {
			str = str + "SF_NORMAL ";
		}
		if ((bits & 0x10000000) != 0) {
			str = str + "SF_MESSAGES_LOST ";
		}
		if ((bits & 0x8000000) != 0) {
			str = str + "SF_DISABLED ";
		}
		if ((bits & 0x4000000) != 0) {
			str = str + "SF_OVERLOAD_IMMINENT ";
		}
		if ((bits & 0x2000000) != 0) {
			str = str + "SF_OVERLOAD_REACHED ";
		}
		if ((bits & 0x1000000) != 0) {
			str = str + "SF_OVERLOAD_RELIEVED ";
		}
		return print(bits, str, name, indent);
	}
}