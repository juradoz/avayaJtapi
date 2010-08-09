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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.CallControlAddressEvent JD-Core Version: 0.5.4
 */