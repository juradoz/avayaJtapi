package javax.telephony.mobile;

public abstract interface MobileRadioListener {
	public abstract void radioOff(MobileRadioEvent paramMobileRadioEvent);

	public abstract void radioOn(MobileRadioEvent paramMobileRadioEvent);

	public abstract void radioStartStateOff(
			MobileRadioEvent paramMobileRadioEvent);

	public abstract void radioStartStateOn(
			MobileRadioEvent paramMobileRadioEvent);

	public abstract void signalLevelChanged(
			MobileRadioEvent paramMobileRadioEvent);
}

