package javax.telephony.privatedata.capabilities;

public abstract interface PrivateDataCapabilities {
	public abstract boolean canGetPrivateData();

	public abstract boolean canSendPrivateData();

	public abstract boolean canSetPrivateData();
}

