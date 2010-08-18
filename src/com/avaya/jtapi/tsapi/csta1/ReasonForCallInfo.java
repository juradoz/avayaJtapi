package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ReasonForCallInfo extends ASNEnumerated {
	public static final short OR_NONE = 0;
	public static final short OR_CONSULTATION = 1;
	public static final short OR_CONFERENCED = 2;
	public static final short OR_TRANSFERRED = 3;
	public static final short OR_NEW_CALL = 4;

	public static Collection<String> print(final short value,
			final String name, final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "OR_NONE";
			break;
		case 1:
			str = "OR_CONSULTATION";
			break;
		case 2:
			str = "OR_CONFERENCED";
			break;
		case 3:
			str = "OR_TRANSFERRED";
			break;
		case 4:
			str = "OR_NEW_CALL";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
