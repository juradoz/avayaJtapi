package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class UserEnteredCodeIndicator extends ASNEnumerated {
	public static final short UE_COLLECT = 0;
	public static final short UE_ENTERED = 1;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "UE_COLLECT";
			break;
		case 1:
			str = "UE_ENTERED";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
