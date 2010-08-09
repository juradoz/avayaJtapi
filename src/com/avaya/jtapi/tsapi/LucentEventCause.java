package com.avaya.jtapi.tsapi;

public abstract interface LucentEventCause {
	public static final short EC_NONE = -1;
	public static final short EC_ACTIVE_MONITOR = 1;
	public static final short EC_ALTERNATE = 2;
	public static final short EC_BUSY = 3;
	public static final short EC_CALL_BACK = 4;
	public static final short EC_CALL_CANCELLED = 5;
	public static final short EC_CALL_FORWARD_ALWAYS = 6;
	public static final short EC_CALL_FORWARD_BUSY = 7;
	public static final short EC_CALL_FORWARD_NO_ANSWER = 8;
	public static final short EC_CALL_FORWARD = 9;
	public static final short EC_CALL_NOT_ANSWERED = 10;
	public static final short EC_CALL_PICKUP = 11;
	public static final short EC_CAMP_ON = 12;
	public static final short EC_DEST_NOT_OBTAINABLE = 13;
	public static final short EC_DO_NOT_DISTURB = 14;
	public static final short EC_INCOMPATIBLE_DESTINATION = 15;
	public static final short EC_INVALID_ACCOUNT_CODE = 16;
	public static final short EC_KEY_CONFERENCE = 17;
	public static final short EC_LOCKOUT = 18;
	public static final short EC_MAINTENANCE = 19;
	public static final short EC_NETWORK_CONGESTION = 20;
	public static final short EC_NETWORK_NOT_OBTAINABLE = 21;
	public static final short EC_NEW_CALL = 22;
	public static final short EC_NO_AVAILABLE_AGENTS = 23;
	public static final short EC_OVERRIDE = 24;
	public static final short EC_PARK = 25;
	public static final short EC_OVERFLOW = 26;
	public static final short EC_RECALL = 27;
	public static final short EC_REDIRECTED = 28;
	public static final short EC_REORDER_TONE = 29;
	public static final short EC_RESOURCES_NOT_AVAILABLE = 30;
	public static final short EC_SILENT_MONITOR = 31;
	public static final short EC_TRANSFER = 32;
	public static final short EC_TRUNKS_BUSY = 33;
	public static final short EC_VOICE_UNIT_INITIATOR = 34;
	public static final short EC_NETWORK_SIGNAL = 46;
	public static final short EC_SINGLE_STEP_TRANSFER = 52;
	public static final short EC_ALERT_TIME_EXPIRED = 60;
	public static final short EC_DESTINATION_OUT_OF_ORDER = 65;
	public static final short EC_NOT_SUPPORTED_BEARER_SERVICE = 80;
	public static final short EC_UNASSIGNED_NUMBER = 81;
	public static final short EC_INCOMPATIBLE_BEARER_SERVICE = 87;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.LucentEventCause JD-Core Version: 0.5.4
 */