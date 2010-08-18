package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class DeviceIDStatus extends ASNEnumerated {
	public static final short ID_PROVIDED = 0;
	public static final short ID_NOT_KNOWN = 1;
	public static final short ID_NOT_REQUIRED = 2;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "ID_PROVIDED";
			break;
		case 1:
			str = "ID_NOT_KNOWN";
			break;
		case 2:
			str = "ID_NOT_REQUIRED";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

