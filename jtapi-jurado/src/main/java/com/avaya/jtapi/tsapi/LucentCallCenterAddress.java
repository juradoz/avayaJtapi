package com.avaya.jtapi.tsapi;

import javax.telephony.CallListener;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;

public abstract interface LucentCallCenterAddress extends LucentAddress {
	public abstract void addCallListener(CallListener paramCallListener,
			boolean paramBoolean) throws ResourceUnavailableException,
			PrivilegeViolationException, MethodNotSupportedException;
}