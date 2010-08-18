package javax.telephony;

import javax.telephony.events.CallEv;

/** @deprecated */
@Deprecated
public abstract interface CallObserver {
	public abstract void callChangedEvent(CallEv[] paramArrayOfCallEv);
}

