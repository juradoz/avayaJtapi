package javax.telephony.phone.events;

import javax.telephony.events.TermEv;
import javax.telephony.phone.Component;
import javax.telephony.phone.ComponentGroup;

/** @deprecated */
@Deprecated
public abstract interface PhoneTermEv extends PhoneEv, TermEv {
	public abstract Component getComponent();

	public abstract ComponentGroup getComponentGroup();
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * javax.telephony.phone.events.PhoneTermEv JD-Core Version: 0.5.4
 */