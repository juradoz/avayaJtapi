package com.avaya.jtapi.tsapi.impl.events.call;

import javax.telephony.MetaEvent;

public class CallEventUtil {
	public static MetaEvent[] getListenerMetaObject(int cause,
			CallEventParams callParams, boolean isSnapshot) {
		int metaEventId = 210;
		boolean isMultiCallMeta = false;
		MetaEvent[] metaEventPair = null;

		if (isSnapshot) {
			metaEventId = 212;
			metaEventPair = new SingleCallMetaEventImpl[2];
			metaEventPair[0] = new SingleCallMetaEventImpl(callParams,
					metaEventId);
			metaEventPair[1] = new SingleCallMetaEventImpl(callParams,
					metaEventId + 1);
		} else {
			switch (cause) {
			case 207:
				metaEventId = 200;
				isMultiCallMeta = true;
				break;
			case 212:
				metaEventId = 202;
				isMultiCallMeta = true;
			}

			if (isMultiCallMeta) {
				metaEventPair = new MultiCallMetaEventImpl[2];
				metaEventPair[0] = new MultiCallMetaEventImpl(callParams,
						metaEventId);
				metaEventPair[1] = new MultiCallMetaEventImpl(callParams,
						metaEventId + 1);
			} else {
				metaEventPair = new SingleCallMetaEventImpl[2];
				metaEventPair[0] = new SingleCallMetaEventImpl(callParams,
						metaEventId);
				metaEventPair[1] = new SingleCallMetaEventImpl(callParams,
						metaEventId + 1);
			}
		}

		return metaEventPair;
	}
}

