package javax.telephony.phone;

import javax.telephony.TerminalListener;

public abstract interface PhoneTerminalListener extends TerminalListener {
	public abstract void terminalButtonInfoChanged(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalButtonPressed(
			PhoneTerminalEvent paramPhoneTerminalEvent);

	public abstract void terminalButtonPressThresholdExceeded(
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
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.phone.PhoneTerminalListener JD-Core Version: 0.5.4
 */