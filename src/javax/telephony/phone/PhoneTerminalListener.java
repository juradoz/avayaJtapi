package javax.telephony.phone;

import javax.telephony.TerminalListener;

public abstract interface PhoneTerminalListener extends TerminalListener {
	public abstract void terminalButtonInfoChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalButtonPressed(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalDisplayUpdated(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalHookswitchStateChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalLampModeChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalMicrophoneGainChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalRingerPatternChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalRingerVolumeChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalSpeakerVolumeChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalButtonPressThresholdExceeded(
			PhoneTerminalEvent paramPhoneTerminalEvent);
}