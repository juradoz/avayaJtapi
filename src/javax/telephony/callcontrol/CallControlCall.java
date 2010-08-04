package javax.telephony.callcontrol;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.Connection;
import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidPartyException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;

public abstract interface CallControlCall extends Call
{
  public abstract Address getCallingAddress();

  public abstract Terminal getCallingTerminal();

  public abstract Address getCalledAddress();

  public abstract Address getLastRedirectedAddress();

  public abstract Connection addParty(String paramString)
    throws InvalidStateException, InvalidPartyException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract void drop()
    throws InvalidStateException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract Connection offHook(Address paramAddress, Terminal paramTerminal)
    throws InvalidStateException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract void conference(Call paramCall)
    throws InvalidStateException, InvalidArgumentException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract void transfer(Call paramCall)
    throws InvalidStateException, InvalidArgumentException, InvalidPartyException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract Connection transfer(String paramString)
    throws InvalidArgumentException, InvalidStateException, InvalidPartyException, MethodNotSupportedException, PrivilegeViolationException, ResourceUnavailableException;

  public abstract void setConferenceController(TerminalConnection paramTerminalConnection)
    throws InvalidArgumentException, InvalidStateException, MethodNotSupportedException, ResourceUnavailableException;

  public abstract TerminalConnection getConferenceController();

  public abstract void setTransferController(TerminalConnection paramTerminalConnection)
    throws InvalidArgumentException, InvalidStateException, MethodNotSupportedException, ResourceUnavailableException;

  public abstract TerminalConnection getTransferController();

  public abstract void setConferenceEnable(boolean paramBoolean)
    throws InvalidArgumentException, InvalidStateException, MethodNotSupportedException, PrivilegeViolationException;

  public abstract boolean getConferenceEnable();

  public abstract void setTransferEnable(boolean paramBoolean)
    throws InvalidArgumentException, InvalidStateException, MethodNotSupportedException, PrivilegeViolationException;

  public abstract boolean getTransferEnable();

  public abstract Connection[] consult(TerminalConnection paramTerminalConnection, String paramString)
    throws InvalidStateException, InvalidArgumentException, MethodNotSupportedException, ResourceUnavailableException, PrivilegeViolationException, InvalidPartyException;

  public abstract Connection consult(TerminalConnection paramTerminalConnection)
    throws InvalidStateException, InvalidArgumentException, MethodNotSupportedException, ResourceUnavailableException, PrivilegeViolationException;
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     javax.telephony.callcontrol.CallControlCall
 * JD-Core Version:    0.5.4
 */