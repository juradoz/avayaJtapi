package javax.telephony.privatedata.capabilities;

public abstract interface PrivateDataCapabilities {
	public abstract boolean canSetPrivateData();

	public abstract boolean canGetPrivateData();

	public abstract boolean canSendPrivateData();
}