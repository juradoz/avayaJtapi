package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public class LucentRedirectType extends ASNEnumerated {
	public static final short VDN = 0;
	public static final short NETWORK = 1;

	public static Collection<String> print(short value, String name,
			String indent) {
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

		return print(value, str, name, indent);
	}
}