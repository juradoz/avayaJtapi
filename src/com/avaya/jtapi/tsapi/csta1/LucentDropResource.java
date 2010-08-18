package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentDropResource extends ASNEnumerated {
	public static final short DR_NONE = -1;
	public static final short DR_CALL_CLASSIFIER = 0;
	public static final short DR_TONE_GENERATOR = 1;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case -1:
			str = "DR_NONE";
			break;
		case 0:
			str = "DR_CALL_CLASSIFIER";
			break;
		case 1:
			str = "DR_TONE_GENERATOR";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
