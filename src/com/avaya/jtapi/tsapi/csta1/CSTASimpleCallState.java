package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTASimpleCallState extends ASNEnumerated {
	static final short CALL_NULL = 0;
	static final short CALL_PENDING = 1;
	static final short CALL_ORIGINATED = 3;
	static final short CALL_DELIVERED = 35;
	static final short CALL_DELIVERED_HELD = 36;
	static final short CALL_RECEIVED = 50;
	static final short CALL_ESTABLISHED = 51;
	static final short CALL_ESTABLISHED_HELD = 52;
	static final short CALL_RECEIVED_ON_HOLD = 66;
	static final short CALL_ESTABLISHED_ON_HOLD = 67;
	static final short CALL_QUEUED = 83;
	static final short CALL_QUEUED_HELD = 84;
	static final short CALL_FAILED = 99;
	static final short CALL_FAILED_HELD = 100;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "CALL_NULL";
			break;
		case 1:
			str = "CALL_PENDING";
			break;
		case 3:
			str = "CALL_ORIGINATED";
			break;
		case 35:
			str = "CALL_DELIVERED";
			break;
		case 36:
			str = "CALL_DELIVERED_HELD";
			break;
		case 50:
			str = "CALL_RECEIVED";
			break;
		case 51:
			str = "CALL_ESTABLISHED";
			break;
		case 52:
			str = "CALL_ESTABLISHED_HELD";
			break;
		case 66:
			str = "CALL_RECEIVED_ON_HOLD";
			break;
		case 67:
			str = "CALL_ESTABLISHED_ON_HOLD";
			break;
		case 83:
			str = "CALL_QUEUED";
			break;
		case 84:
			str = "CALL_QUEUED_HELD";
			break;
		case 99:
			str = "CALL_FAILED";
			break;
		case 100:
			str = "CALL_FAILED_HELD";
			break;
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
