package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ConnectionIDDevice extends ASNEnumerated {
	public static final short STATIC_ID = 0;
	public static final short DYNAMIC_ID = 1;

	public static Collection<String> print(final short value,
			final String name, final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "STATIC_ID";
			break;
		case 1:
			str = "DYNAMIC_ID";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
