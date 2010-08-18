package javax.telephony.mobile;

import javax.telephony.MethodNotSupportedException;
import javax.telephony.Terminal;

public abstract interface MobileTerminal extends Terminal {
	public abstract boolean generateDTMF(String paramString)
			throws MethodNotSupportedException;

	public abstract String getManufacturerName();

	public abstract String getSoftwareVersion();

	public abstract String getTerminalId();

	public abstract String getTypeApproval();

	public abstract boolean startDTMF(char paramChar)
			throws MethodNotSupportedException;

	public abstract void stopDTMF();
}

