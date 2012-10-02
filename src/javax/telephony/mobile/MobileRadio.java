package javax.telephony.mobile;

import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;

public abstract interface MobileRadio {
	public abstract boolean getRadioState() throws MethodNotSupportedException;

	public abstract void setRadioState(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException;

	public abstract boolean getRadioStartState()
			throws MethodNotSupportedException;

	public abstract void setRadioStartState(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException;

	public abstract int getSignalLevel();

	public abstract int getMaxSignalLevel();

	public abstract void addRadioListener(
			MobileRadioListener paramMobileRadioListener)
			throws MethodNotSupportedException;

	public abstract void removeRadioListener(
			MobileRadioListener paramMobileRadioListener);
}