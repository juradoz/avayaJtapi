package com.avaya.jtapi.tsapi.csta1;

import java.io.OutputStream;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;

public final class CSTACallFilter extends ASNBitString {
	static final int CF_CALL_CLEARED = -2147483648;
	static final int CF_CONFERENCED = 1073741824;
	static final int CF_CONNECTION_CLEARED = 536870912;
	static final int CF_DELIVERED = 268435456;
	static final int CF_DIVERTED = 134217728;
	static final int CF_ESTABLISHED = 67108864;
	static final int CF_FAILED = 33554432;
	static final int CF_HELD = 16777216;
	static final int CF_NETWORK_REACHED = 8388608;
	static final int CF_ORIGINATED = 4194304;
	static final int CF_QUEUED = 2097152;
	static final int CF_RETRIEVED = 1048576;
	static final int CF_SERVICE_INITIATED = 524288;
	static final int CF_TRANSFERRED = 262144;
	static final int numBits = 14;

	static void encode(int bits, OutputStream out) {
		encode(bits, 14, out);
	}

	static Collection<String> print(int bits, String name, String indent) {
		String str = " ";

		if ((bits & 0x80000000) != 0) {
			str = str + "CF_CALL_CLEARED ";
		}
		if ((bits & 0x40000000) != 0) {
			str = str + "CF_CONFERENCED ";
		}
		if ((bits & 0x20000000) != 0) {
			str = str + "CF_CONNECTION_CLEARED ";
		}
		if ((bits & 0x10000000) != 0) {
			str = str + "CF_DELIVERED ";
		}
		if ((bits & 0x8000000) != 0) {
			str = str + "CF_DIVERTED ";
		}
		if ((bits & 0x4000000) != 0) {
			str = str + "CF_ESTABLISHED ";
		}
		if ((bits & 0x2000000) != 0) {
			str = str + "CF_FAILED ";
		}
		if ((bits & 0x1000000) != 0) {
			str = str + "CF_HELD ";
		}
		if ((bits & 0x800000) != 0) {
			str = str + "CF_NETWORK_REACHED ";
		}
		if ((bits & 0x400000) != 0) {
			str = str + "CF_ORIGINATED ";
		}
		if ((bits & 0x200000) != 0) {
			str = str + "CF_QUEUED ";
		}
		if ((bits & 0x100000) != 0) {
			str = str + "CF_RETRIEVED ";
		}
		if ((bits & 0x80000) != 0) {
			str = str + "CF_SERVICE_INITIATED ";
		}
		if ((bits & 0x40000) != 0) {
			str = str + "CF_TRANSFERRED ";
		}

		return print(bits, str, name, indent);
	}
}

