package javax.telephony.mobile;

import javax.telephony.Event;

public abstract interface MobileRadioEvent extends Event {
	public static final int MOBILERADIO_SIGNAL_LEVEL_CHANGED = 2;
	public static final int MOBILERADIO_STARTSTATE_ON = 4;
	public static final int MOBILERADIO_STARTSTATE_OFF = 6;
	public static final int MOBILERADIO_RADIO_ON = 8;
	public static final int MOBILERADIO_RADIO_OFF = 9;

	public abstract MobileRadio getMobileRadio();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.mobile.MobileRadioEvent JD-Core Version: 0.5.4
 */