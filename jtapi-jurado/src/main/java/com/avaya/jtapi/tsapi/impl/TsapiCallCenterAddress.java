package com.avaya.jtapi.tsapi.impl;

import com.avaya.jtapi.tsapi.LucentCallCenterAddress;
import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
import com.avaya.jtapi.tsapi.impl.core.TSDevice;
import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
import com.avaya.jtapi.tsapi.util.TsapiTrace;
import javax.telephony.CallListener;
import javax.telephony.MethodNotSupportedException;
import javax.telephony.PrivilegeViolationException;
import javax.telephony.ResourceUnavailableException;

public class TsapiCallCenterAddress extends TsapiAddress implements
		LucentCallCenterAddress {
	TsapiCallCenterAddress(TSDevice tsDevice) {
		super(tsDevice);
	}

	TsapiCallCenterAddress(TSProviderImpl TSProviderImpl,
			CSTAExtendedDeviceID deviceID) throws TsapiInvalidArgumentException {
		super(TSProviderImpl, deviceID);
	}

	TsapiCallCenterAddress(TSProviderImpl TSProviderImpl, String number)
			throws TsapiInvalidArgumentException {
		super(TSProviderImpl, number);
	}

	public void addCallListener(CallListener listener, boolean remain)
			throws ResourceUnavailableException, PrivilegeViolationException,
			MethodNotSupportedException {
		TsapiTrace.traceEntry("addCallListener[CallListener listener]", this);
		addCallEventMonitor(null, remain, listener);
		TsapiTrace.traceExit("addCallListener[CallListener listener]", this);
	}
}