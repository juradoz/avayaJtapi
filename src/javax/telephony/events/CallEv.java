package javax.telephony.events;

import javax.telephony.Call;

public abstract interface CallEv extends Ev {
	public abstract Call getCall();
}

