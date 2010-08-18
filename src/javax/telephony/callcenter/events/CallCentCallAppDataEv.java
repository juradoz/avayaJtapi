package javax.telephony.callcenter.events;

/** @deprecated */
@Deprecated
public abstract interface CallCentCallAppDataEv extends CallCentCallEv {
	public static final int ID = 316;

	public abstract Object getApplicationData();
}
