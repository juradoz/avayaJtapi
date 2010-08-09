package javax.telephony.callcontrol;

import javax.telephony.Address;
import javax.telephony.Connection;
import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;

public abstract interface CallControlTerminal extends Terminal {
	public abstract boolean getDoNotDisturb()
			throws MethodNotSupportedException;

	public abstract TerminalConnection pickup(Address paramAddress1,
			Address paramAddress2) throws InvalidArgumentException,
			InvalidStateException, MethodNotSupportedException,
			PrivilegeViolationException, ResourceUnavailableException;

	public abstract TerminalConnection pickup(Connection paramConnection,
			Address paramAddress) throws InvalidArgumentException,
			InvalidStateException, MethodNotSupportedException,
			PrivilegeViolationException, ResourceUnavailableException;

	public abstract TerminalConnection pickup(
			TerminalConnection paramTerminalConnection, Address paramAddress)
			throws InvalidArgumentException, InvalidStateException,
			MethodNotSupportedException, PrivilegeViolationException,
			ResourceUnavailableException;

	public abstract TerminalConnection pickupFromGroup(Address paramAddress)
			throws InvalidArgumentException, InvalidStateException,
			MethodNotSupportedException, PrivilegeViolationException,
			ResourceUnavailableException;

	public abstract TerminalConnection pickupFromGroup(String paramString,
			Address paramAddress) throws InvalidArgumentException,
			InvalidStateException, MethodNotSupportedException,
			PrivilegeViolationException, ResourceUnavailableException;

	public abstract void setDoNotDisturb(boolean paramBoolean)
			throws MethodNotSupportedException, InvalidStateException;
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.CallControlTerminal JD-Core Version: 0.5.4
 */