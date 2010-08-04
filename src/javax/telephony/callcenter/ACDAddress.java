package javax.telephony.callcenter;

import javax.telephony.Call;
import javax.telephony.MethodNotSupportedException;

public abstract interface ACDAddress extends CallCenterAddress
{
  public abstract Agent[] getLoggedOnAgents()
    throws MethodNotSupportedException;

  public abstract int getNumberQueued()
    throws MethodNotSupportedException;

  public abstract Call getOldestCallQueued()
    throws MethodNotSupportedException;

  public abstract int getRelativeQueueLoad()
    throws MethodNotSupportedException;

  public abstract int getQueueWaitTime()
    throws MethodNotSupportedException;

  public abstract ACDManagerAddress getACDManagerAddress()
    throws MethodNotSupportedException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcenter.ACDAddress
 * JD-Core Version:    0.5.4
 */