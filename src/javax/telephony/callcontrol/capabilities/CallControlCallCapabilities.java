package javax.telephony.callcontrol.capabilities;

import javax.telephony.Call;
import javax.telephony.TerminalConnection;
import javax.telephony.capabilities.CallCapabilities;

public abstract interface CallControlCallCapabilities extends CallCapabilities {
	public abstract boolean canAddParty();

	public abstract boolean canConference();

	/** @deprecated */
	@Deprecated
	public abstract boolean canConsult();

	public abstract boolean canConsult(
			TerminalConnection paramTerminalConnection);

	public abstract boolean canConsult(
			TerminalConnection paramTerminalConnection, String paramString);

	public abstract boolean canDrop();

	public abstract boolean canOffHook();

	public abstract boolean canSetConferenceController();

	public abstract boolean canSetConferenceEnable();

	public abstract boolean canSetTransferController();

	public abstract boolean canSetTransferEnable();

	/** @deprecated */
	@Deprecated
	public abstract boolean canTransfer();

	public abstract boolean canTransfer(Call paramCall);

	public abstract boolean canTransfer(String paramString);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.callcontrol.capabilities.CallControlCallCapabilities JD-Core
 * Version: 0.5.4
 */