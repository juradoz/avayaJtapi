package javax.telephony.phone;

public abstract interface PhoneButton extends Component {
	public abstract String getInfo();

	public abstract void setInfo(String paramString);

	public abstract PhoneLamp getAssociatedPhoneLamp();

	public abstract void buttonPress();

	public abstract int setButtonPressThreshold(int paramInt);

	public abstract int getButtonPressThreshold();
}