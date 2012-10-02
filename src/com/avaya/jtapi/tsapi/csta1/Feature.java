package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public final class Feature extends ASNEnumerated {
	static final short FT_CAMP_ON = 0;
	static final short FT_CALL_BACK = 1;
	static final short FT_INTRUDE = 2;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "FT_CAMP_ON";
			break;
		case 1:
			str = "FT_CALL_BACK";
			break;
		case 2:
			str = "FT_INTRUDE";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}