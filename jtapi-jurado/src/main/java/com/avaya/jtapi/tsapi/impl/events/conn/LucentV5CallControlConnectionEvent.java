package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import javax.telephony.MetaEvent;

public class LucentV5CallControlConnectionEvent extends
		LucentCallControlConnectionEvent implements LucentV5CallInfo {
	public LucentV5CallControlConnectionEvent(CallEventParams params,
			MetaEvent event, int eventId, int numInQueue, String digits) {
		super(params, event, eventId, numInQueue, digits);
	}

	public boolean canSetBillRate() {
		return this.callEventParams.isFlexibleBilling();
	}

	public int getCallOriginatorType() {
		return this.callEventParams.getCallOriginatorType();
	}

	public String getUCID() {
		return this.callEventParams.getUcid();
	}

	public boolean hasCallOriginatorType() {
		return this.callEventParams.hasCallOriginatorType();
	}
}