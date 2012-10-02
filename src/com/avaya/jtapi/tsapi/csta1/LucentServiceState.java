package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public class LucentServiceState extends ASNEnumerated {
	public static final short UNKNOWN = -1;
	public static final short OUT_OF_SERVICE = 0;
	public static final short IN_SERVICE = 1;

	public static Collection<String> print(short value, String name,
			String indent) {
		String str;
		switch (value) {
		case -1:
			str = "UNKNOWN";
			break;
		case 0:
			str = "OUT_OF_SERVICE";
			break;
		case 1:
			str = "IN_SERVICE";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}