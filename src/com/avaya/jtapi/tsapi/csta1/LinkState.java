package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class LinkState extends ASNEnumerated {
	public static final short LS_LINK_UNAVAIL = 0;
	public static final short LS_LINK_UP = 1;
	public static final short LS_LINK_DOWN = 2;

	public static Collection<String> print(final short value,
			final String name, final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "LS_LINK_UNAVAIL";
			break;
		case 1:
			str = "LS_LINK_UP";
			break;
		case 2:
			str = "LS_LINK_DOWN";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
