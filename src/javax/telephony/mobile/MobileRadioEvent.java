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

