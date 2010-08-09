package javax.telephony.callcontrol;

import javax.telephony.Address;
import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;

public abstract interface CallControlAddress extends Address {
	public abstract void cancelForwarding() throws MethodNotSupportedException,
			InvalidStateException;

	public abstract boolean getDoNotDisturb()
			throws MethodNotSupportedException;

	public abstract CallControlForwarding[] getForwarding()
			throws MethodNotSupportedException;

	public abstract boolean getMessageWaiting()
			throws MethodNotSupportedException;

	public abstract void setDoNotDisturb(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException;

	public abstract void setForwarding(
			CallControlForwarding[] paramArrayOfCallControlForwarding)
			throws MethodNotSupportedException, InvalidStateException,
			InvalidArgumentException;

	public abstract void setMessageWaiting(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.CallControlAddress JD-Core Version: 0.5.4
 */