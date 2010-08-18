package javax.telephony.mobile;

import javax.telephony.ProviderEvent;

public abstract interface MobileProviderEvent extends ProviderEvent {
	public static final int CAUSE_NORMAL = 0;
	public static final int CAUSE_FORBIDDEN = 1;
	public static final int CAUSE_FORBIDDEN_ZONE = 2;
	public static final int CAUSE_SUSBCRIPTION_ERROR = 3;
	public static final int CAUSE_RADIO_OFF = 4;
	public static final int CAUSE_NO_NETWORK = 5;
	public static final int CAUSE_NETWORK_NOT_SELECTED = 6;
	public static final int CAUSE_SEARCHING = 7;
	public static final int CAUSE_ILLEGAL_MOBILE = 8;
	public static final int CAUSE_UNKNOWN = 153;
	public static final int PROVIDER_RESTRICTED_SERVICE = 257;
	public static final int MOBILEPROVIDER_NETWORK_CHANGE = 3;
	public static final int MOBILEPROVIDER_NO_NETWORK = 8;

	public abstract MobileProvider getMobileProvider();

	public abstract int getMobileStateCause();
}
