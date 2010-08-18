package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentAnswerTreat extends ASNEnumerated {
	public static final short AT_NO_TREATMENT = 0;
	public static final short AT_NONE = 1;
	public static final short AT_DROP = 2;
	public static final short AT_CONNECT = 3;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "AT_NO_TREATMENT";
			break;
		case 1:
			str = "AT_NONE";
			break;
		case 2:
			str = "AT_DROP";
			break;
		case 3:
			str = "AT_CONNECT";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
