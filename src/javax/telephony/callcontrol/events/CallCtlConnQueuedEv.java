package javax.telephony.callcontrol.events;

/** @deprecated */
@Deprecated
public abstract interface CallCtlConnQueuedEv extends CallCtlConnEv {
	public static final int ID = 212;

	public abstract int getNumberInQueue();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.events.CallCtlConnQueuedEv JD-Core Version: 0.5.4
 */