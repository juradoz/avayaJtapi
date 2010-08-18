package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ProgressLocation extends ASNEnumerated {
	public static final short PL_USER = 0;
	public static final short PL_PUB_LOCAL = 1;
	public static final short PL_PUB_REMOTE = 4;
	public static final short PL_PRIV_REMOTE = 5;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "PL_USER";
			break;
		case 1:
			str = "PL_PUB_LOCAL";
			break;
		case 4:
			str = "PL_PUB_REMOTE";
			break;
		case 5:
			str = "PL_PRIV_REMOTE";
			break;
		case 2:
		case 3:
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
