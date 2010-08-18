package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class SelectValue extends ASNEnumerated {
	public static final short SV_NORMAL = 0;
	public static final short SV_LEAST_COST = 1;
	public static final short SV_EMERGENCY = 2;
	public static final short SV_ACD = 3;
	public static final short SV_USER_DEFINED = 4;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "SV_NORMAL";
			break;
		case 1:
			str = "SV_LEAST_COST";
			break;
		case 2:
			str = "SV_EMERGENCY";
			break;
		case 3:
			str = "SV_ACD";
			break;
		case 4:
			str = "SV_USER_DEFINED";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

