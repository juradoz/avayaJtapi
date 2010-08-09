package javax.telephony.callcontrol;

import javax.telephony.ConnectionEvent;

public abstract interface CallControlConnectionEvent extends
		CallControlCallEvent, ConnectionEvent {
	public static final int CALLCTL_CONNECTION_ALERTING = 353;
	public static final int CALLCTL_CONNECTION_DIALING = 354;
	public static final int CALLCTL_CONNECTION_DISCONNECTED = 355;
	public static final int CALLCTL_CONNECTION_ESTABLISHED = 356;
	public static final int CALLCTL_CONNECTION_FAILED = 357;
	public static final int CALLCTL_CONNECTION_INITIATED = 358;
	public static final int CALLCTL_CONNECTION_NETWORK_ALERTING = 359;
	public static final int CALLCTL_CONNECTION_NETWORK_REACHED = 360;
	public static final int CALLCTL_CONNECTION_OFFERED = 361;
	public static final int CALLCTL_CONNECTION_QUEUED = 362;
	public static final int CALLCTL_CONNECTION_UNKNOWN = 363;

	public abstract String getDigits();

	public abstract int getNumberInQueue();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.CallControlConnectionEvent JD-Core Version: 0.5.4
 */