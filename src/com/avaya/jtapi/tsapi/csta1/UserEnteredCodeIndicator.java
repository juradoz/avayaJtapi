package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public final class UserEnteredCodeIndicator extends ASNEnumerated {
	public static final short UE_COLLECT = 0;
	public static final short UE_ENTERED = 1;

	static Collection<String> print(short value, String name, String indent) {
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

		return print(value, str, name, indent);
	}
}