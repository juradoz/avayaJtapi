package javax.telephony.privatedata;

import javax.telephony.TerminalListener;

public abstract interface PrivateDataTerminalListener extends TerminalListener {
	public abstract void terminalPrivateData(
			PrivateDataEvent paramPrivateDataEvent);
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.privatedata.PrivateDataTerminalListener JD-Core Version:
 * 0.5.4
 */