package javax.telephony.callcontrol.events;

/** @deprecated */
@Deprecated
public abstract interface CallCtlAddrDoNotDisturbEv extends CallCtlAddrEv {
	public static final int ID = 200;

	public abstract boolean getDoNotDisturbState();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.events.CallCtlAddrDoNotDisturbEv JD-Core Version:
 * 0.5.4
 */