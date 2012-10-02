package javax.telephony.callcontrol.events;

/** @deprecated */
public abstract interface CallCtlAddrMessageWaitingEv extends CallCtlAddrEv {
	public static final int ID = 202;

	public abstract boolean getMessageWaitingState();
}