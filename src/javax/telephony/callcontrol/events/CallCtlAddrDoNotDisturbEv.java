package javax.telephony.callcontrol.events;

/** @deprecated */
@Deprecated
public abstract interface CallCtlAddrDoNotDisturbEv extends CallCtlAddrEv {
	public static final int ID = 200;

	public abstract boolean getDoNotDisturbState();
}
