package com.avaya.jtapi.tsapi.impl.events.call;

import com.avaya.jtapi.tsapi.CSTACauseVariant;
import com.avaya.jtapi.tsapi.ITsapiCallEvent;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.TsapiPrivate;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;
import javax.telephony.Call;
import javax.telephony.CallEvent;
import javax.telephony.MetaEvent;
import javax.telephony.privatedata.PrivateDataEvent;

public class CallEventImpl implements CallEvent, PrivateDataEvent,
		ITsapiCallEvent {
	protected CallEventParams callEventParams;
	private MetaEvent metaEvent;
	private int id;

	public CallEventImpl(CallEventParams params, MetaEvent event, int eventId) {
		this.callEventParams = params;
		this.metaEvent = event;
		this.id = eventId;
	}

	public Call getCall() {
		return this.callEventParams.getCall();
	}

	public int getID() {
		return this.id;
	}

	public MetaEvent getMetaEvent() {
		return this.metaEvent;
	}

	public Object getSource() {
		return this.callEventParams.getCall();
	}

	public int getCause() {
		int cause = this.callEventParams.getCause();
		if ((cause == 101) || (cause == 102) || (cause == 103)
				|| (cause == 104) || (cause == 105) || (cause == 106)
				|| (cause == 107) || (cause == 108) || (cause == 109)
				|| (cause == 110)) {
			return cause;
		}
		return 100;
	}

	public Object getPrivateData() {
		Object privateData = this.callEventParams.getPrivateData();
		if (((privateData instanceof TsapiPrivate))
				|| ((privateData instanceof LucentChargeAdviceEvent))
				|| ((privateData instanceof TsapiPrivateStateEvent))) {
			return privateData;
		}
		return null;
	}

	public short getCSTACause() {
		return this.callEventParams.getCstaCause();
	}

	public short getCSTACause(CSTACauseVariant cstaCauseVariant) {
		short retValue;
		if (cstaCauseVariant == CSTACauseVariant.override) {
			retValue = this.callEventParams.getCsta3Cause();
		} else {
			retValue = this.callEventParams.getCstaCause();
		}
		return retValue;
	}
}