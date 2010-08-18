package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class ProgressDescription extends ASNEnumerated {
	public static final short PD_CALL_OFF_ISDN = 1;
	public static final short PD_DEST_NOT_ISDN = 2;
	public static final short PD_ORIG_NOT_ISDN = 3;
	public static final short PD_CALL_ON_ISDN = 4;
	public static final short PD_INBAND = 8;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 1:
			str = "PD_CALL_OFF_ISDN";
			break;
		case 2:
			str = "PD_DEST_NOT_ISDN";
			break;
		case 3:
			str = "PD_ORIG_NOT_ISDN";
			break;
		case 4:
			str = "PD_CALL_ON_ISDN";
			break;
		case 8:
			str = "PD_INBAND";
			break;
		case 5:
		case 6:
		case 7:
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
