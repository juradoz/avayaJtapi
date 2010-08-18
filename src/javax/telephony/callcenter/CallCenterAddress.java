package javax.telephony.callcenter;

import javax.telephony.Address;
import javax.telephony.CallObserver;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;

@SuppressWarnings("deprecation")
public abstract interface CallCenterAddress extends Address {

	public abstract void addCallObserver(CallObserver paramCallObserver,
			boolean paramBoolean) throws ResourceUnavailableException,
			PrivilegeViolationException, MethodNotSupportedException;
}

