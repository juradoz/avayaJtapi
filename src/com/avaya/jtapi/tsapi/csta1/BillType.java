package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class BillType extends ASNEnumerated {
	public static final short BT_NEW_RATE = 16;
	public static final short BT_FLAT_RATE = 17;
	public static final short BT_PREMIUM_CHARGE = 18;
	public static final short BT_PREMIUM_CREDIT = 19;
	public static final short BT_FREE_CALL = 24;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 16:
			str = "BT_NEW_RATE";
			break;
		case 17:
			str = "BT_FLAT_RATE";
			break;
		case 18:
			str = "BT_PREMIUM_CHARGE";
			break;
		case 19:
			str = "BT_PREMIUM_CREDIT";
			break;
		case 24:
			str = "BT_FREE_CALL";
			break;
		case 20:
		case 21:
		case 22:
		case 23:
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.csta1.BillType JD-Core Version: 0.5.4
 */