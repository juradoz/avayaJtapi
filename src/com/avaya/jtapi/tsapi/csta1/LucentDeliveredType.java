package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentDeliveredType extends ASNEnumerated {
	public static final short DELIVERED_TO_ACD = 1;
	public static final short DELIVERED_TO_STATION = 2;
	public static final short DELIVERED_OTHER = 3;

	public static Collection<String> print(short value, String name,
			String indent) {
		String str;
		switch (value) {
		case 1:
			str = "DELIVERED_TO_ACD";
			break;
		case 2:
			str = "DELIVERED_TO_STATION";
			break;
		case 3:
			str = "DELIVERED_OTHER";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

