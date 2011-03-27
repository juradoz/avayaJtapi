package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.MetaEvent;

import com.avaya.jtapi.tsapi.ITsapiConnNetworkReachedListenerEvent;
import com.avaya.jtapi.tsapi.NetworkProgressInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class CallControlConnectionNetworkReachedEventImpl extends
		CallControlConnectionEventImpl implements
		ITsapiConnNetworkReachedListenerEvent {
	public CallControlConnectionNetworkReachedEventImpl(
			final CallEventParams params, final MetaEvent event,
			final int eventId, final int numInQueue, final String digits) {
		super(params, event, eventId, numInQueue, digits);
	}

	@Override
	public NetworkProgressInfo getNetworkProgressInfo() {
		final Object privateData = callEventParams.getPrivateData();
		if (privateData instanceof NetworkProgressInfo)
			return (NetworkProgressInfo) privateData;
		return null;
	}
}
