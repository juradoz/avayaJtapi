package com.avaya.jtapi.tsapi.impl.events.termConn;

import javax.telephony.MetaEvent;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class LucentV5CallControlTerminalConnectionEvent extends
		LucentCallControlTerminalConnectionEvent implements LucentV5CallInfo {
	public LucentV5CallControlTerminalConnectionEvent(CallEventParams params,
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

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.termConn.
 * LucentV5CallControlTerminalConnectionEvent JD-Core Version: 0.5.4
 */