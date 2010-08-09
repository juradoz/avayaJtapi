package javax.telephony.phone;

public abstract interface PhoneSpeaker extends Component {
	public static final int MUTE = 0;
	public static final int MID = 50;
	public static final int FULL = 100;

	public abstract int getVolume();

	public abstract void setVolume(int paramInt);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.phone.PhoneSpeaker JD-Core Version: 0.5.4
 */