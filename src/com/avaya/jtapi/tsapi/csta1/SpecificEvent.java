package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class SpecificEvent extends ASNEnumerated {
	public static final short SE_ANSWER = 11;
	public static final short SE_DISCONNECT = 4;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 11:
			str = "SE_ANSWER";
			break;
		case 4:
			str = "SE_DISCONNECT";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

