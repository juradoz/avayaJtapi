package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LucentRedirectType extends ASNEnumerated {
	public static final short VDN = 0;
	public static final short NETWORK = 1;

	public static Collection<String> print(final short value,
			final String name, final String indent) {
		String str;
		switch (value) {
		case 1:
			str = "NETWORK";
			break;
		case 0:
			str = "VDN";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
