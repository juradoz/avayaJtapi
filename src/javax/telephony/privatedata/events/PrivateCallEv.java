package javax.telephony.privatedata.events;

import javax.telephony.events.CallEv;

/** @deprecated */
@Deprecated
public abstract interface PrivateCallEv extends CallEv {
	public static final int ID = 601;

	public abstract Object getPrivateData();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.privatedata.events.PrivateCallEv JD-Core Version: 0.5.4
 */