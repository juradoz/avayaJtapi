package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class Feature extends ASNEnumerated {
	static final short FT_CAMP_ON = 0;
	static final short FT_CALL_BACK = 1;
	static final short FT_INTRUDE = 2;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "FT_CAMP_ON";
			break;
		case 1:
			str = "FT_CALL_BACK";
			break;
		case 2:
			str = "FT_INTRUDE";
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
 * com.avaya.jtapi.tsapi.csta1.Feature JD-Core Version: 0.5.4
 */