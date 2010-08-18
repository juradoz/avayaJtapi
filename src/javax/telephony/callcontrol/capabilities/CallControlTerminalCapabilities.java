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

