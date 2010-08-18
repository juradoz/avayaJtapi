package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.MetaEvent;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class LucentV5CallCenterConnectionEvent extends
		LucentCallCenterConnectionEvent implements LucentV5CallInfo {
	public LucentV5CallCenterConnectionEvent(CallEventParams params,
			MetaEvent event, int eventId) {
		super(params, event, eventId);
	}

	public boolean canSetBillRate() {
		return callEventParams.isFlexibleBilling();
	}

	public int getCallOriginatorType() {
		return callEventParams.getCallOriginatorType();
	}

	public String getUCID() {
		return callEventParams.getUcid();
	}

	public boolean hasCallOriginatorType() {
		return callEventParams.hasCallOriginatorType();
	}
}
