package javax.telephony.phone;

import javax.telephony.InvalidArgumentException;

public abstract interface PhoneLamp extends Component {
	public static final int LAMPMODE_OFF = 0;
	public static final int LAMPMODE_FLASH = 1;
	public static final int LAMPMODE_STEADY = 2;
	public static final int LAMPMODE_FLUTTER = 3;
	public static final int LAMPMODE_BROKENFLUTTER = 4;
	public static final int LAMPMODE_WINK = 5;

	public abstract PhoneButton getAssociatedPhoneButton();

	public abstract int getMode();

	public abstract int[] getSupportedModes();

	public abstract void setMode(int paramInt) throws InvalidArgumentException;
}

