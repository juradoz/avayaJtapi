package javax.telephony.mobile;

public abstract interface MobileNetwork {
	public abstract boolean isAvailable();

	public abstract boolean isCurrent();

	public abstract boolean isRestricted();

	public abstract String[] getNames();

	public abstract String getCode();
}