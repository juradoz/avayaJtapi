package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class AllocationState extends ASNEnumerated {
	public static final short AS_CALL_DELIVERED = 0;
	public static final short AS_CALL_ESTABLISHED = 1;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "AS_CALL_DELIVERED";
			break;
		case 1:
			str = "AS_CALL_ESTABLISHED";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
