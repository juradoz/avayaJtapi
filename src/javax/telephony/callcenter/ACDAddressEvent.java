package javax.telephony.callcenter;

import javax.telephony.AddressEvent;

public abstract interface ACDAddressEvent extends CallCenterEvent, AddressEvent {
	public static final int ACD_ADDRESS_BUSY = 300;
	public static final int ACD_ADDRESS_LOGGED_OFF = 301;
	public static final int ACD_ADDRESS_LOGGED_ON = 302;
	public static final int ACD_ADDRESS_NOT_READY = 303;
	public static final int ACD_ADDRESS_READY = 304;
	public static final int ACD_ADDRESS_UNKNOWN = 305;
	public static final int ACD_ADDRESS_WORK_NOT_READY = 306;
	public static final int ACD_ADDRESS_WORK_READY = 307;

	public abstract Agent getAgent();
}

