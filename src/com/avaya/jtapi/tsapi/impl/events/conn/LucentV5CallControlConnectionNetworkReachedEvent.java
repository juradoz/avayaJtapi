package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.MetaEvent;

import com.avaya.jtapi.tsapi.LucentV5Call;
import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class LucentV5CallControlConnectionNetworkReachedEvent extends
		LucentCallControlConnectionNetworkReachedEvent implements
		LucentV5CallInfo {
	public LucentV5CallControlConnectionNetworkReachedEvent(
			final CallEventParams params, final MetaEvent event,
			final int eventId, final int numInQueue, final String digits) {
		super(params, event, eventId, numInQueue, digits);
	}

	@Override
	public boolean canSetBillRate() {
		return callEventParams.isFlexibleBilling();
	}

	@Override
	public int getCallOriginatorType() {
		return callEventParams.getCallOriginatorType();
	}

	@Override
	public String getUCID() {
		String ucid = null;
		if (callEventParams.getUcid() != null)
			ucid = callEventParams.getUcid();
		else if (getCall() instanceof LucentV5Call)
			ucid = ((LucentV5Call) getCall()).getUCID();
		return ucid;
	}

	@Override
	public boolean hasCallOriginatorType() {
		return callEventParams.hasCallOriginatorType();
	}
}
