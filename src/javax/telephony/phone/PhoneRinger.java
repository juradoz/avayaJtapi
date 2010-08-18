package javax.telephony.phone;

import javax.telephony.InvalidArgumentException;

public abstract interface PhoneRinger extends Component {
	public static final int OFF = 0;
	public static final int MIDDLE = 50;
	public static final int FULL = 100;

	public abstract int getNumberOfRingPatterns();

	public abstract int getNumberOfRings();

	public abstract int getRingerPattern();

	public abstract int getRingerVolume();

	public abstract int isRingerOn();

	public abstract void setRingerPattern(int paramInt)
			throws InvalidArgumentException;

	public abstract void setRingerVolume(int paramInt)
			throws InvalidArgumentException;
}
