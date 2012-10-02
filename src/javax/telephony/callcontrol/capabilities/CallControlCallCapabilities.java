package javax.telephony.callcontrol.capabilities;

import javax.telephony.Call;
import javax.telephony.TerminalConnection;
import javax.telephony.capabilities.CallCapabilities;

public abstract interface CallControlCallCapabilities extends CallCapabilities {
	public abstract boolean canDrop();

	public abstract boolean canOffHook();

	public abstract boolean canSetConferenceController();

	public abstract boolean canSetTransferController();

	public abstract boolean canSetTransferEnable();

	public abstract boolean canSetConferenceEnable();

	/** @deprecated */
	public abstract boolean canTransfer();

	public abstract boolean canTransfer(Call paramCall);

	public abstract boolean canTransfer(String paramString);

	public abstract boolean canConference();

	public abstract boolean canAddParty();

	/** @deprecated */
	public abstract boolean canConsult();

	public abstract boolean canConsult(
			TerminalConnection paramTerminalConnection, String paramString);

	public abstract boolean canConsult(
			TerminalConnection paramTerminalConnection);
}