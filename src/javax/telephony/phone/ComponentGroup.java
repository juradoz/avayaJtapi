package javax.telephony.phone;

import javax.telephony.Address;
import javax.telephony.InvalidArgumentException;
import javax.telephony.phone.capabilities.ComponentGroupCapabilities;

public abstract interface ComponentGroup {
	public static final int HEAD_SET = 1;
	public static final int HAND_SET = 2;
	public static final int SPEAKER_PHONE = 3;
	public static final int PHONE_SET = 4;
	public static final int OTHER = 5;

	public abstract boolean activate();

	public abstract boolean activate(Address paramAddress)
			throws InvalidArgumentException;

	public abstract boolean deactivate();

	public abstract boolean deactivate(Address paramAddress)
			throws InvalidArgumentException;

	public abstract ComponentGroupCapabilities getCapabilities();

	public abstract Component[] getComponents();

	public abstract String getDescription();

	public abstract int getType();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.phone.ComponentGroup JD-Core Version: 0.5.4
 */