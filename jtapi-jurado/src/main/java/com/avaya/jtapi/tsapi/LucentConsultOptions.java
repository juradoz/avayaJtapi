package com.avaya.jtapi.tsapi;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public class LucentConsultOptions extends ASNEnumerated {
	public static final short UNRESTRICTED = 0;
	public static final short CONSULT_ONLY = 1;
	public static final short TRANSFER_ONLY = 2;
	public static final short CONFERENCE_ONLY = 3;

	public static Collection<String> print(short value, String name,
			String indent) {
		String str;
		switch (value) {
		case 0:
			str = "UNRESTRICTED";
			break;
		case 1:
			str = "CONSULT_ONLY";
			break;
		case 2:
			str = "TRANSFER_ONLY";
			break;
		case 3:
			str = "CONFERENCE_ONLY";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}

	public static boolean validate(short consultOptions) {
		if ((consultOptions < 0) || (consultOptions > 3)) {
			return false;
		}
		return true;
	}
}