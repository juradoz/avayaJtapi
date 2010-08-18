package javax.telephony.phone;

public abstract interface PhoneButton extends Component {
	public abstract void buttonPress();

	public abstract PhoneLamp getAssociatedPhoneLamp();

	public abstract int getButtonPressThreshold();

	public abstract String getInfo();

	public abstract int setButtonPressThreshold(int paramInt);

	public abstract void setInfo(String paramString);
}
