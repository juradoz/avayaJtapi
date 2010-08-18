package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentWorkMode extends ASNEnumerated {
	public static final short WM_NONE = -1;
	public static final short WM_AUX_WORK = 1;
	public static final short WM_AFTCAL_WK = 2;
	public static final short WM_AUTO_IN = 3;
	public static final short WM_MANUAL_IN = 4;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case -1:
			str = "WM_NONE";
			break;
		case 1:
			str = "WM_AUX_WORK";
			break;
		case 2:
			str = "WM_AFTCAL_WK";
			break;
		case 3:
			str = "WM_AUTO_IN";
			break;
		case 4:
			str = "WM_MANUAL_IN";
			break;
		case 0:
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

