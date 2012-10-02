package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public final class AllocationState extends ASNEnumerated {
	public static final short AS_CALL_DELIVERED = 0;
	public static final short AS_CALL_ESTABLISHED = 1;

	static Collection<String> print(short value, String name, String indent) {
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

		return print(value, str, name, indent);
	}
}