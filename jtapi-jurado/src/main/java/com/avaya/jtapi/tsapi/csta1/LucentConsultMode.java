package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public class LucentConsultMode extends ASNEnumerated {
	public static final short ATT_CM_NONE = 0;
	public static final short ATT_CM_CONSULTATION = 1;
	public static final short ATT_CM_TRANSFER = 2;
	public static final short ATT_CM_CONFERENCE = 3;
	public static final short ATT_CM_NOT_PROVIDED = 4;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 0:
			str = "ATT_CM_NONE";
			break;
		case 1:
			str = "ATT_CM_CONSULTATION";
			break;
		case 2:
			str = "ATT_CM_TRANSFER";
			break;
		case 3:
			str = "ATT_CM_CONFERENCE";
			break;
		case 4:
			str = "ATT_CM_NOT_PROVIDED";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}