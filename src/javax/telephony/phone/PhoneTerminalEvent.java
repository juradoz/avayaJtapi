package javax.telephony.phone;

import javax.telephony.Event;
import javax.telephony.TerminalEvent;

public abstract interface PhoneTerminalEvent extends Event, TerminalEvent {
	public static final int CAUSE_PHONE_TERMINAL_NORMAL = 500;
	public static final int CAUSE_PHONE_TERMINAL_UNKNOWN = 501;
	public static final int PHONE_TERMINAL_BUTTON_INFO_EVENT = 500;
	public static final int PHONE_TERMINAL_BUTTON_PRESS_EVENT = 501;
	public static final int PHONE_TERMINAL_DISPLAY_UPDATE_EVENT = 502;
	public static final int PHONE_TERMINAL_HOOKSWITCH_STATE_EVENT = 503;
	public static final int PHONE_TERMINAL_LAMP_MODE_EVENT = 504;
	public static final int PHONE_TERMINAL_MICROPHONE_GAIN_EVENT = 505;
	public static final int PHONE_TERMINAL_RINGER_PATTERN_EVENT = 506;
	public static final int PHONE_TERMINAL_RINGER_VOLUME_EVENT = 507;
	public static final int PHONE_TERMINAL_SPEAKER_VOLUME_EVENT = 508;
	public static final int PHONE_TERMINAL_BUTTON_PRESS_THRESHOLD_EXCEEDED_EVENT = 509;

	public abstract Component getComponent();

	public abstract ComponentGroup getComponentGroup();

	public abstract String getDisplay(int paramInt1, int paramInt2);

	public abstract int getGain();

	public abstract int getHookSwitchState();

	public abstract String getInfo();

	public abstract int getMode();

	public abstract String getOldInfo();

	public abstract int getPhoneCause();

	public abstract int getPressedDuration();

	public abstract int getRingerPattern();

	public abstract int getVolume();
}
