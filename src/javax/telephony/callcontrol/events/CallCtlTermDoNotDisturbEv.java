package javax.telephony.callcontrol.events;

/** @deprecated */
@Deprecated
public abstract interface CallCtlTermDoNotDisturbEv extends CallCtlTermEv {
	public static final int ID = 221;

	public abstract boolean getDoNotDisturbState();
}

