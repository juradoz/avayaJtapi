package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.callcenter.events.CallCentCallAppDataEv;

@SuppressWarnings("deprecation")
public class TsapiCallCentCallAppDataEv extends TsapiCallEvent implements
		CallCentCallAppDataEv {
	private Object applicationData;

	public TsapiCallCentCallAppDataEv(CallEventParams params,
			Object applicationData) {
		super(params);
		this.applicationData = applicationData;
	}

	public int getID() {
		return 316;
	}

	public Object getApplicationData() {
		return this.applicationData;
	}
}