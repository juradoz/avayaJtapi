package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class SDBLevel extends ASNEnumerated {
	static final short NO_SDB_CHECKING = -1;
	static final short ACS_ONLY = 1;
	static final short ACS_AND_CSTA_CHECKING = 0;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case -1:
			str = "NO_SDB_CHECKING";
			break;
		case 1:
			str = "ACS_ONLY";
			break;
		case 0:
			str = "ACS_AND_CSTA_CHECKING";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
