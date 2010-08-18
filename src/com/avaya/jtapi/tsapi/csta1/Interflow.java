package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class Interflow extends ASNEnumerated {
	public static final short LAI_NO_INTERFLOW = -1;
	public static final short LAI_ALL_INTERFLOW = 0;
	public static final short LAI_THRESHOLD_INTERFLOW = 1;
	public static final short LAI_VECTORING_INTERFLOW = 2;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case -1:
			str = "LAI_NO_INTERFLOW";
			break;
		case 0:
			str = "LAI_ALL_INTERFLOW";
			break;
		case 1:
			str = "LAI_THRESHOLD_INTERFLOW";
			break;
		case 2:
			str = "LAI_VECTORING_INTERFLOW";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
