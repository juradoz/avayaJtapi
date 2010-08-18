package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class LucentDeviceType extends ASNEnumerated {
	public static final short ATT_DT_ACD_SPLIT = 1;
	public static final short ATT_DT_ANNOUNCEMENT = 2;
	public static final short ATT_DT_DATA = 3;
	public static final short ATT_DT_LOGICAL_AGENT = 4;
	public static final short ATT_DT_STATION = 5;
	public static final short ATT_DT_TRUNK_ACCESS_CODE = 6;
	public static final short ATT_DT_VDN = 7;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 1:
			str = "ATT_DT_ACD_SPLIT";
			break;
		case 2:
			str = "ATT_DT_ANNOUNCEMENT";
			break;
		case 3:
			str = "ATT_DT_DATA";
			break;
		case 4:
			str = "ATT_DT_LOGICAL_AGENT";
			break;
		case 5:
			str = "ATT_DT_STATION";
			break;
		case 6:
			str = "ATT_DT_TRUNK_ACCESS_CODE";
			break;
		case 7:
			str = "ATT_DT_VDN";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}

