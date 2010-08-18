package javax.telephony.privatedata;

public abstract interface PrivateData {
	public abstract Object getPrivateData();

	public abstract Object sendPrivateData(Object paramObject);

	public abstract void setPrivateData(Object paramObject);
}
