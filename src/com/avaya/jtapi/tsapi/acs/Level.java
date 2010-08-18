package com.avaya.jtapi.tsapi.acs;

import java.util.ArrayList;
import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class Level extends ASNEnumerated {
	public static final short ACS_LEVEL1 = 1;
	public static final short ACS_LEVEL2 = 2;
	public static final short ACS_LEVEL3 = 3;
	public static final short ACS_LEVEL4 = 4;

	static Collection<String> print(short value, String name, String indent) {
		Collection<String> lines = new ArrayList<String>();
		String str;
		switch (value) {
		case 1:
			str = "ACS_LEVEL1";
			break;
		case 2:
			str = "ACS_LEVEL2";
			break;
		case 3:
			str = "ACS_LEVEL3";
			break;
		case 4:
			str = "ACS_LEVEL4";
			break;
		default:
			str = "?? " + value + " ??";
		}

		lines.addAll(print(value, str, name, indent));
		return lines;
	}
}

