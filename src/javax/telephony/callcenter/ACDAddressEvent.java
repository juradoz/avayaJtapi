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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcenter.ACDAddressEvent JD-Core Version: 0.5.4
 */