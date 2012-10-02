package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public final class CSTALevel extends ASNEnumerated {
	public static final short CSTA_HOME_WORK_TOP = 1;
	public static final short CSTA_AWAY_WORK_TOP = 2;
	public static final short CSTA_DEVICE_DEVICE_MONITOR = 3;
	public static final short CSTA_CALL_DEVICE_MONITOR = 4;
	public static final short CSTA_CALL_CONTROL = 5;
	public static final short CSTA_ROUTING = 6;
	public static final short CSTA_CALL_CALL_MONITOR = 7;

	static Collection<String> print(short value, String name, String indent) {
		String str;
		switch (value) {
		case 1:
			str = "CSTA_HOME_WORK_TOP";
			break;
		case 2:
			str = "CSTA_AWAY_WORK_TOP";
			break;
		case 3:
			str = "CSTA_DEVICE_DEVICE_MONITOR";
			break;
		case 4:
			str = "CSTA_CALL_DEVICE_MONITOR";
			break;
		case 5:
			str = "CSTA_CALL_CONTROL";
			break;
		case 6:
			str = "CSTA_ROUTING";
			break;
		case 7:
			str = "CSTA_CALL_CALL_MONITOR";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}