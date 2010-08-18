package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentTalkState extends ASNEnumerated {
	public static final short TS_ON_CALL = 0;
	public static final short TS_IDLE = 1;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "TS_ON_CALL";
			break;
		case 1:
			str = "TS_IDLE";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
