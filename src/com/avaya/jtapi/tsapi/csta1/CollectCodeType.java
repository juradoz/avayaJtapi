package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CollectCodeType extends ASNEnumerated {
	public static final short UC_NONE = 0;
	public static final short UC_TONE_DETECTOR = 32;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "UC_NONE";
			break;
		case 32:
			str = "UC_TONE_DETECTOR";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
