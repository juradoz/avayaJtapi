package javax.telephony;

import java.util.EventListener;

public abstract interface AddressListener extends EventListener {
	public abstract void addressListenerEnded(AddressEvent paramAddressEvent);
}

