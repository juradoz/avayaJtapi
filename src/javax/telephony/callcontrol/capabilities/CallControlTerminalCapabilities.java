package javax.telephony.callcontrol.capabilities;

import javax.telephony.Address;
import javax.telephony.Connection;
import javax.telephony.TerminalConnection;
import javax.telephony.capabilities.TerminalCapabilities;

public abstract interface CallControlTerminalCapabilities extends
		TerminalCapabilities {
	public abstract boolean canGetDoNotDisturb();

	/** @deprecated */
	@Deprecated
	public abstract boolean canPickup();

	public abstract boolean canPickup(Address paramAddress1,
			Address paramAddress2);

	public abstract boolean canPickup(Connection paramConnection,
			Address paramAddress);

	public abstract boolean canPickup(
			TerminalConnection paramTerminalConnection, Address paramAddress);

	/** @deprecated */
	@Deprecated
	public abstract boolean canPickupFromGroup();

	public abstract boolean canPickupFromGroup(Address paramAddress);

	public abstract boolean canPickupFromGroup(String paramString,
			Address paramAddress);

	public abstract boolean canSetDoNotDisturb();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.capabilities.CallControlTerminalCapabilities
 * JD-Core Version: 0.5.4
 */