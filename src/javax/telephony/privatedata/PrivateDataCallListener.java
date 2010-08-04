package javax.telephony.privatedata;

import javax.telephony.CallListener;

public abstract interface PrivateDataCallListener extends CallListener
{
  public abstract void callPrivateData(PrivateDataEvent paramPrivateDataEvent);
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.privatedata.PrivateDataCallListener
 * JD-Core Version:    0.5.4
 */