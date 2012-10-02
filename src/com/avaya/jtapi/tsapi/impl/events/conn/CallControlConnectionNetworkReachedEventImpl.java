package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.ITsapiConnNetworkReachedListenerEvent;
import com.avaya.jtapi.tsapi.NetworkProgressInfo;
import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import javax.telephony.MetaEvent;

public class CallControlConnectionNetworkReachedEventImpl extends
		CallControlConnectionEventImpl implements
		ITsapiConnNetworkReachedListenerEvent {
	public CallControlConnectionNetworkReachedEventImpl(CallEventParams params,
			MetaEvent event, int eventId, int numInQueue, String digits) {
		super(params, event, eventId, numInQueue, digits);
	}

	public NetworkProgressInfo getNetworkProgressInfo() {
		Object privateData = this.callEventParams.getPrivateData();
		if ((privateData instanceof NetworkProgressInfo))
			return (NetworkProgressInfo) privateData;
		return null;
	}
}