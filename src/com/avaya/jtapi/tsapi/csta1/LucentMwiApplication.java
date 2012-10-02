package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNBitString;
import java.io.OutputStream;
import java.util.Collection;

public final class LucentMwiApplication extends ASNBitString {
	static final int AT_MCS = 16777216;
	static final int AT_VOICE = 33554432;
	static final int AT_PROPMGT = 67108864;
	static final int AT_LWC = 134217728;
	static final int AT_CTI = 268435456;
	static final int numBits = 8;

	static void encode(int bits, OutputStream out) {
		encode(bits, 8, out);
	}

	static Collection<String> print(int bits, String name, String indent) {
		String str = " ";

		if ((bits & 0x1000000) != 0) {
			str = str + "AT_MCS ";
		}
		if ((bits & 0x2000000) != 0) {
			str = str + "AT_VOICE ";
		}
		if ((bits & 0x4000000) != 0) {
			str = str + "AT_PROPMGT ";
		}
		if ((bits & 0x8000000) != 0) {
			str = str + "AT_LWC ";
		}
		if ((bits & 0x10000000) != 0) {
			str = str + "AT_CTI ";
		}

		return print(bits, str, name, indent);
	}
}