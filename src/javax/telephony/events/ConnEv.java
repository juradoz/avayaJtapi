package javax.telephony.events;

import javax.telephony.Connection;

public abstract interface ConnEv extends CallEv {
	public abstract Connection getConnection();
}