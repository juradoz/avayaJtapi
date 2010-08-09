package javax.telephony.phone;

import javax.telephony.phone.capabilities.ComponentCapabilities;

public abstract interface Component {
	public abstract ComponentCapabilities getCapabilities();

	public abstract String getName();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.phone.Component JD-Core Version: 0.5.4
 */