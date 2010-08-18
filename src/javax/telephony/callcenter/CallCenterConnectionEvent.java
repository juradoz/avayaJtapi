package javax.telephony.callcenter;

import javax.telephony.ConnectionEvent;

public abstract interface CallCenterConnectionEvent extends
		CallCenterCallEvent, ConnectionEvent {
	public static final int CALLCENT_CONNECTION_IN_PROGRESS = 319;
}
