package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ForwardingType extends ASNEnumerated {
	public static final short FWD_IMMEDIATE = 0;
	public static final short FWD_BUSY = 1;
	public static final short FWD_NO_ANS = 2;
	public static final short FWD_BUSY_INT = 3;
	public static final short FWD_BUSY_EXT = 4;
	public static final short FWD_NO_ANS_INT = 5;
	public static final short FWD_NO_ANS_EXT = 6;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "FWD_IMMEDIATE";
			break;
		case 1:
			str = "FWD_BUSY";
			break;
		case 2:
			str = "FWD_NO_ANS";
			break;
		case 3:
			str = "FWD_BUSY_INT";
			break;
		case 4:
			str = "FWD_BUSY_EXT";
			break;
		case 5:
			str = "FWD_NO_ANS_INT";
			break;
		case 6:
			str = "FWD_NO_ANS_EXT";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

