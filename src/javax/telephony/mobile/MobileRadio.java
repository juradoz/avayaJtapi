package javax.telephony.mobile;

import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;

public abstract interface MobileRadio {
	public abstract void addRadioListener(
			MobileRadioListener paramMobileRadioListener)
			throws MethodNotSupportedException;

	public abstract int getMaxSignalLevel();

	public abstract boolean getRadioStartState()
			throws MethodNotSupportedException;

	public abstract boolean getRadioState() throws MethodNotSupportedException;

	public abstract int getSignalLevel();

	public abstract void removeRadioListener(
			MobileRadioListener paramMobileRadioListener);

	public abstract void setRadioStartState(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException;

	public abstract void setRadioState(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.mobile.MobileRadio JD-Core Version: 0.5.4
 */