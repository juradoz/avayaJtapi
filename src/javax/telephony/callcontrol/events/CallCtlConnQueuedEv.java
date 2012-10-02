package javax.telephony.callcontrol.events;

/** @deprecated */
public abstract interface CallCtlConnQueuedEv extends CallCtlConnEv {
	public static final int ID = 212;

	public abstract int getNumberInQueue();
}