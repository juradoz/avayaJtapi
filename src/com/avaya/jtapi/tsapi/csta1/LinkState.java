package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public class LinkState extends ASNEnumerated {
	public static final short LS_LINK_UNAVAIL = 0;
	public static final short LS_LINK_UP = 1;
	public static final short LS_LINK_DOWN = 2;

	public static Collection<String> print(short value, String name,
			String indent) {
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

		return print(value, str, name, indent);
	}
}