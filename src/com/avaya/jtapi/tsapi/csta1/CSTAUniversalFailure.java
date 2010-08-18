package com.avaya.jtapi.tsapi.csta1;

import java.util.Collection;

import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;

public final class CSTAUniversalFailure extends ASNEnumerated {
	public static final short GENERIC_UNSPECIFIED = 0;
	public static final short GENERIC_OPERATION = 1;
	public static final short REQUEST_INCOMPATIBLE_WITH_OBJECT = 2;
	public static final short VALUE_OUT_OF_RANGE = 3;
	public static final short OBJECT_NOT_KNOWN = 4;
	public static final short INVALID_CALLING_DEVICE = 5;
	public static final short INVALID_CALLED_DEVICE = 6;
	public static final short INVALID_FORWARDING_DESTINATION = 7;
	public static final short PRIVILEGE_VIOLATION_ON_SPECIFIED_DEVICE = 8;
	public static final short PRIVILEGE_VIOLATION_ON_CALLED_DEVICE = 9;
	public static final short PRIVILEGE_VIOLATION_ON_CALLING_DEVICE = 10;
	public static final short INVALID_CSTA_CALL_IDENTIFIER = 11;
	public static final short INVALID_CSTA_DEVICE_IDENTIFIER = 12;
	public static final short INVALID_CSTA_CONNECTION_IDENTIFIER = 13;
	public static final short INVALID_DESTINATION = 14;
	public static final short INVALID_FEATURE = 15;
	public static final short INVALID_ALLOCATION_STATE = 16;
	public static final short INVALID_CROSS_REF_ID = 17;
	public static final short INVALID_OBJECT_TYPE = 18;
	public static final short SECURITY_VIOLATION = 19;
	public static final short GENERIC_STATE_INCOMPATIBILITY = 21;
	public static final short INVALID_OBJECT_STATE = 22;
	public static final short INVALID_CONNECTION_ID_FOR_ACTIVE_CALL = 23;
	public static final short NO_ACTIVE_CALL = 24;
	public static final short NO_HELD_CALL = 25;
	public static final short NO_CALL_TO_CLEAR = 26;
	public static final short NO_CONNECTION_TO_CLEAR = 27;
	public static final short NO_CALL_TO_ANSWER = 28;
	public static final short NO_CALL_TO_COMPLETE = 29;
	public static final short GENERIC_SYSTEM_RESOURCE_AVAILABILITY = 31;
	public static final short SERVICE_BUSY = 32;
	public static final short RESOURCE_BUSY = 33;
	public static final short RESOURCE_OUT_OF_SERVICE = 34;
	public static final short NETWORK_BUSY = 35;
	public static final short NETWORK_OUT_OF_SERVICE = 36;
	public static final short OVERALL_MONITOR_LIMIT_EXCEEDED = 37;
	public static final short CONFERENCE_MEMBER_LIMIT_EXCEEDED = 38;
	public static final short GENERIC_SUBSCRIBED_RESOURCE_AVAILABILITY = 41;
	public static final short OBJECT_MONITOR_LIMIT_EXCEEDED = 42;
	public static final short EXTERNAL_TRUNK_LIMIT_EXCEEDED = 43;
	public static final short OUTSTANDING_REQUEST_LIMIT_EXCEEDED = 44;
	public static final short GENERIC_PERFORMANCE_MANAGEMENT = 51;
	public static final short PERFORMANCE_LIMIT_EXCEEDED = 52;
	public static final short UNSPECIFIED_SECURITY_ERROR = 60;
	public static final short SEQUENCE_NUMBER_VIOLATED = 61;
	public static final short TIME_STAMP_VIOLATED = 62;
	public static final short PAC_VIOLATED = 63;
	public static final short SEAL_VIOLATED = 64;
	public static final short GENERIC_UNSPECIFIED_REJECTION = 70;
	public static final short GENERIC_OPERATION_REJECTION = 71;
	public static final short DUPLICATE_INVOCATION_REJECTION = 72;
	public static final short UNRECOGNIZED_OPERATION_REJECTION = 73;
	public static final short MISTYPED_ARGUMENT_REJECTION = 74;
	public static final short RESOURCE_LIMITATION_REJECTION = 75;
	public static final short ACS_HANDLE_TERMINATION_REJECTION = 76;
	public static final short SERVICE_TERMINATION_REJECTION = 77;
	public static final short REQUEST_TIMEOUT_REJECTION = 78;
	public static final short REQUESTS_ON_DEVICE_EXCEEDED_REJECTION = 79;
	public static final short UNRECOGNIZED_APDU_REJECTION = 80;
	public static final short MISTYPED_APDU_REJECTION = 81;
	public static final short BADLY_STRUCTURED_APDU_REJECTION = 82;
	public static final short INITIATOR_RELEASING_REJECTION = 83;
	public static final short UNRECOGNIZED_LINKEDID_REJECTION = 84;
	public static final short LINKED_RESPONSE_UNEXPECTED_REJECTION = 85;
	public static final short UNEXPECTED_CHILD_OPERATION_REJECTION = 86;
	public static final short MISTYPED_RESULT_REJECTION = 87;
	public static final short UNRECOGNIZED_ERROR_REJECTION = 88;
	public static final short UNEXPECTED_ERROR_REJECTION = 89;
	public static final short MISTYPED_PARAMETER_REJECTION = 90;
	public static final short NON_STANDARD = 100;

	static Collection<String> print(final short value, final String name,
			final String indent) {
		String str;
		switch (value) {
		case 0:
			str = "GENERIC_UNSPECIFIED";
			break;
		case 1:
			str = "GENERIC_OPERATION";
			break;
		case 2:
			str = "REQUEST_INCOMPATIBLE_WITH_OBJECT";
			break;
		case 3:
			str = "VALUE_OUT_OF_RANGE";
			break;
		case 4:
			str = "OBJECT_NOT_KNOWN";
			break;
		case 5:
			str = "INVALID_CALLING_DEVICE";
			break;
		case 6:
			str = "INVALID_CALLED_DEVICE";
			break;
		case 7:
			str = "INVALID_FORWARDING_DESTINATION";
			break;
		case 8:
			str = "PRIVILEGE_VIOLATION_ON_SPECIFIED_DEVICE";
			break;
		case 9:
			str = "PRIVILEGE_VIOLATION_ON_CALLED_DEVICE";
			break;
		case 10:
			str = "PRIVILEGE_VIOLATION_ON_CALLING_DEVICE";
			break;
		case 11:
			str = "INVALID_CSTA_CALL_IDENTIFIER";
			break;
		case 12:
			str = "INVALID_CSTA_DEVICE_IDENTIFIER";
			break;
		case 13:
			str = "INVALID_CSTA_CONNECTION_IDENTIFIER";
			break;
		case 14:
			str = "INVALID_DESTINATION";
			break;
		case 15:
			str = "INVALID_FEATURE";
			break;
		case 16:
			str = "INVALID_ALLOCATION_STATE";
			break;
		case 17:
			str = "INVALID_CROSS_REF_ID";
			break;
		case 18:
			str = "INVALID_OBJECT_TYPE";
			break;
		case 19:
			str = "SECURITY_VIOLATION";
			break;
		case 21:
			str = "GENERIC_STATE_INCOMPATIBILITY";
			break;
		case 22:
			str = "INVALID_OBJECT_STATE";
			break;
		case 23:
			str = "INVALID_CONNECTION_ID_FOR_ACTIVE_CALL";
			break;
		case 24:
			str = "NO_ACTIVE_CALL";
			break;
		case 25:
			str = "NO_HELD_CALL";
			break;
		case 26:
			str = "NO_CALL_TO_CLEAR";
			break;
		case 27:
			str = "NO_CONNECTION_TO_CLEAR";
			break;
		case 28:
			str = "NO_CALL_TO_ANSWER";
			break;
		case 29:
			str = "NO_CALL_TO_COMPLETE";
			break;
		case 31:
			str = "GENERIC_SYSTEM_RESOURCE_AVAILABILITY";
			break;
		case 32:
			str = "SERVICE_BUSY";
			break;
		case 33:
			str = "RESOURCE_BUSY";
			break;
		case 34:
			str = "RESOURCE_OUT_OF_SERVICE";
			break;
		case 35:
			str = "NETWORK_BUSY";
			break;
		case 36:
			str = "NETWORK_OUT_OF_SERVICE";
			break;
		case 37:
			str = "OVERALL_MONITOR_LIMIT_EXCEEDED";
			break;
		case 38:
			str = "CONFERENCE_MEMBER_LIMIT_EXCEEDED";
			break;
		case 41:
			str = "GENERIC_SUBSCRIBED_RESOURCE_AVAILABILITY";
			break;
		case 42:
			str = "OBJECT_MONITOR_LIMIT_EXCEEDED";
			break;
		case 43:
			str = "EXTERNAL_TRUNK_LIMIT_EXCEEDED";
			break;
		case 44:
			str = "OUTSTANDING_REQUEST_LIMIT_EXCEEDED";
			break;
		case 51:
			str = "GENERIC_PERFORMANCE_MANAGEMENT";
			break;
		case 52:
			str = "PERFORMANCE_LIMIT_EXCEEDED";
			break;
		case 60:
			str = "UNSPECIFIED_SECURITY_ERROR";
			break;
		case 61:
			str = "SEQUENCE_NUMBER_VIOLATED";
			break;
		case 62:
			str = "TIME_STAMP_VIOLATED";
			break;
		case 63:
			str = "PAC_VIOLATED";
			break;
		case 64:
			str = "SEAL_VIOLATED";
			break;
		case 70:
			str = "GENERIC_UNSPECIFIED_REJECTION";
			break;
		case 71:
			str = "GENERIC_OPERATION_REJECTION";
			break;
		case 72:
			str = "DUPLICATE_INVOCATION_REJECTION";
			break;
		case 73:
			str = "UNRECOGNIZED_OPERATION_REJECTION";
			break;
		case 74:
			str = "MISTYPED_ARGUMENT_REJECTION";
			break;
		case 75:
			str = "RESOURCE_LIMITATION_REJECTION";
			break;
		case 76:
			str = "ACS_HANDLE_TERMINATION_REJECTION";
			break;
		case 77:
			str = "SERVICE_TERMINATION_REJECTION";
			break;
		case 78:
			str = "REQUEST_TIMEOUT_REJECTION";
			break;
		case 79:
			str = "REQUESTS_ON_DEVICE_EXCEEDED_REJECTION";
			break;
		case 80:
			str = "UNRECOGNIZED_APDU_REJECTION";
			break;
		case 81:
			str = "MISTYPED_APDU_REJECTION";
			break;
		case 82:
			str = "BADLY_STRUCTURED_APDU_REJECTION";
			break;
		case 83:
			str = "INITIATOR_RELEASING_REJECTION";
			break;
		case 84:
			str = "UNRECOGNIZED_LINKEDID_REJECTION";
			break;
		case 85:
			str = "LINKED_RESPONSE_UNEXPECTED_REJECTION";
			break;
		case 86:
			str = "UNEXPECTED_CHILD_OPERATION_REJECTION";
			break;
		case 87:
			str = "MISTYPED_RESULT_REJECTION";
			break;
		case 88:
			str = "UNRECOGNIZED_ERROR_REJECTION";
			break;
		case 89:
			str = "UNEXPECTED_ERROR_REJECTION";
			break;
		case 90:
			str = "MISTYPED_PARAMETER_REJECTION";
			break;
		case 100:
			str = "NON_STANDARD";
			break;
		case 20:
		case 30:
		case 39:
		case 40:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 50:
		case 53:
		case 54:
		case 55:
		case 56:
		case 57:
		case 58:
		case 59:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
		case 91:
		case 92:
		case 93:
		case 94:
		case 95:
		case 96:
		case 97:
		case 98:
		case 99:
		default:
			str = "?? " + value + " ??";
		}

		return ASNEnumerated.print(value, str, name, indent);
	}
}
