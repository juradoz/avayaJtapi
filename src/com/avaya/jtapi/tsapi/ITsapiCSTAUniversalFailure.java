package com.avaya.jtapi.tsapi;

public abstract interface ITsapiCSTAUniversalFailure {
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
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.ITsapiCSTAUniversalFailure JD-Core Version: 0.5.4
 */