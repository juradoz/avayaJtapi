package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class ChargeType extends ASNEnumerated {
	public static final short CT_INTERMEDIATE_CHARGE = 1;
	public static final short CT_FINAL_CHARGE = 2;
	public static final short CT_SPLIT_CHARGE = 3;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 1:
			str = "CT_INTERMEDIATE_CHARGE";
			break;
		case 2:
			str = "CT_FINAL_CHARGE";
			break;
		case 3:
			str = "CT_SPLIT_CHARGE";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
