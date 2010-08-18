package javax.telephony.phone;

public abstract interface PhoneSpeaker extends Component {
	public static final int MUTE = 0;
	public static final int MID = 50;
	public static final int FULL = 100;

	public abstract int getVolume();

	public abstract void setVolume(int paramInt);
}

