package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public class ChargeType extends ASNEnumerated {
	public static final short CT_INTERMEDIATE_CHARGE = 1;
	public static final short CT_FINAL_CHARGE = 2;
	public static final short CT_SPLIT_CHARGE = 3;

	static Collection<String> print(short value, String name, String indent) {
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

		return print(value, str, name, indent);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.ChargeType JD-Core Version: 0.5.4
 */