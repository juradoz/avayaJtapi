package javax.telephony.mobile;

public abstract interface MobileNetwork {
	public abstract String getCode();

	public abstract String[] getNames();

	public abstract boolean isAvailable();

	public abstract boolean isCurrent();

	public abstract boolean isRestricted();
}

