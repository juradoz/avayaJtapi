package javax.telephony.callcontrol;

import javax.telephony.AddressEvent;

public abstract interface CallControlAddressEvent extends CallControlEvent,
		AddressEvent {
	public static final int CALLCTL_ADDRESS_EVENT_DO_NOT_DISTURB = 350;
	public static final int CALLCTL_ADDRESS_EVENT_FORWARD = 351;
	public static final int CALLCTL_ADDRESS_EVENT_MESSAGE_WAITING = 352;

	public abstract boolean getDoNotDisturbState();

	public abstract CallControlForwarding[] getForwarding();

	public abstract boolean getMessageWaitingState();
}
