package javax.telephony.privatedata;

import javax.telephony.CallListener;

public abstract interface PrivateDataCallListener extends CallListener {
	public abstract void callPrivateData(PrivateDataEvent paramPrivateDataEvent);
}