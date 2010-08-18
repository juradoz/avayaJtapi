package javax.telephony.phone;

import javax.telephony.InvalidArgumentException;

public abstract interface PhoneMicrophone extends Component {
	public static final int MUTE = 0;
	public static final int MID = 50;
	public static final int FULL = 100;

	public abstract int getGain();

	public abstract void setGain(int paramInt) throws InvalidArgumentException;
}

