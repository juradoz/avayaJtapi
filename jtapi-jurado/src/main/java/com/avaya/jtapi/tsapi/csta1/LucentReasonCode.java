package com.avaya.jtapi.tsapi.csta1;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
import java.util.Collection;

public final class LucentReasonCode extends ASNEnumerated {
	public static final short AR_NONE = 0;
	public static final short AR_ANSWER_NORMAL = 1;
	public static final short AR_ANSWER_TIMED = 2;
	public static final short AR_ANSWER_VOICE_ENERGY = 3;
	public static final short AR_ANSWER_MACHINE_DETECTED = 4;
	public static final short AR_SIT_REORDER = 5;
	public static final short AR_SIT_NO_CIRCUIT = 6;
	public static final short AR_SIT_INTERCEPT = 7;
	public static final short AR_SIT_VACANT_CODE = 8;
	public static final short AR_SIT_INEFFECTIVE_OTHER = 9;
	public static final short AR_SIT_UNKNOWN = 10;
	public static final short AR_IN_QUEUE = 11;
	public static final short AR_SERVICE_OBSERVER = 12;

	public static Collection<String> print(short value, String name,
			String indent) {
		String str;
		switch (value) {
		case 0:
			str = "AR_NONE";
			break;
		case 1:
			str = "AR_ANSWER_NORMAL";
			break;
		case 2:
			str = "AR_ANSWER_TIMED";
			break;
		case 3:
			str = "AR_ANSWER_VOICE_ENERGY";
			break;
		case 4:
			str = "AR_ANSWER_MACHINE_DETECTED";
			break;
		case 5:
			str = "AR_SIT_REORDER";
			break;
		case 6:
			str = "AR_SIT_NO_CIRCUIT";
			break;
		case 7:
			str = "AR_SIT_INTERCEPT";
			break;
		case 8:
			str = "AR_SIT_VACANT_CODE";
			break;
		case 9:
			str = "AR_SIT_INEFFECTIVE_OTHER";
			break;
		case 10:
			str = "AR_SIT_UNKNOWN";
			break;
		case 11:
			str = "AR_IN_QUEUE";
			break;
		case 12:
			str = "AR_SERVICE_OBSERVER";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return print(value, str, name, indent);
	}
}