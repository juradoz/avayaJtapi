package javax.telephony.privatedata.capabilities;

public abstract interface PrivateDataCapabilities
{
  public abstract boolean canSetPrivateData();

  public abstract boolean canGetPrivateData();

  public abstract boolean canSendPrivateData();
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.privatedata.capabilities.PrivateDataCapabilities
 * JD-Core Version:    0.5.4
 */