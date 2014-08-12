package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public final class CollectCodeType extends ASNEnumerated {
	public static final short UC_NONE = 0;
	public static final short UC_TONE_DETECTOR = 32;

	static Collection<String> print(short value, String name, String indent) {
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

		return print(value, str, name, indent);
	}
}