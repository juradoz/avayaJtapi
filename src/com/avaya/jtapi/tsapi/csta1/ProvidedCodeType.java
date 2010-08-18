package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ProvidedCodeType extends ASNEnumerated {
	public static final short UP_NONE = 0;
	public static final short UP_DATA_BASE_PROVIDED = 17;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "UP_NONE";
			break;
		case 17:
			str = "UP_DATA_BASE_PROVIDED";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

