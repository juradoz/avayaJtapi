package javax.telephony.callcontrol.events;

import javax.telephony.events.Ev;

/** @deprecated */
@Deprecated
public abstract interface CallCtlEv extends Ev {
	public static final int CAUSE_ALTERNATE = 202;
	public static final int CAUSE_BUSY = 203;
	public static final int CAUSE_CALL_BACK = 204;
	public static final int CAUSE_CALL_NOT_ANSWERED = 205;
	public static final int CAUSE_CALL_PICKUP = 206;
	public static final int CAUSE_CONFERENCE = 207;
	public static final int CAUSE_DO_NOT_DISTURB = 208;
	public static final int CAUSE_PARK = 209;
	public static final int CAUSE_REDIRECTED = 210;
	public static final int CAUSE_REORDER_TONE = 211;
	public static final int CAUSE_TRANSFER = 212;
	public static final int CAUSE_TRUNKS_BUSY = 213;
	public static final int CAUSE_UNHOLD = 214;

	public abstract int getCallControlCause();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.events.CallCtlEv JD-Core Version: 0.5.4
 */