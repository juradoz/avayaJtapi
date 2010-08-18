package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcenter.CallCenterCallEvent;
import javax.telephony.callcenter.CallCenterCallListener;
import javax.telephony.callcenter.CallCenterTrunkEvent;

public class CallCenterCallListenerAdapter extends CallListenerAdapter
		implements CallCenterCallListener {
	public void applicationData(CallCenterCallEvent event) {
	}

	public void trunkInvalid(CallCenterTrunkEvent event) {
	}

	public void trunkValid(CallCenterTrunkEvent event) {
	}
}

