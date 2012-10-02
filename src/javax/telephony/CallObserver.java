package javax.telephony;

import javax.telephony.events.CallEv;

/** @deprecated */
public abstract interface CallObserver {
	public abstract void callChangedEvent(CallEv[] paramArrayOfCallEv);
}