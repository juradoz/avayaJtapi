package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.MetaEvent;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class LucentV5CallControlConnectionEvent extends
		LucentCallControlConnectionEvent implements LucentV5CallInfo {
	public LucentV5CallControlConnectionEvent(final CallEventParams params,
			final MetaEvent event, final int eventId, final int numInQueue,
			final String digits) {
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
		return callEventParams.getUcid();
	}

	@Override
	public boolean hasCallOriginatorType() {
		return callEventParams.hasCallOriginatorType();
	}
}
