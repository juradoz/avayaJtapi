package com.avaya.jtapi.tsapi.impl.events;

import com.avaya.jtapi.tsapi.ITsapiEvent;
import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
import com.avaya.jtapi.tsapi.TsapiPrivate;

public abstract class TsapiObserverEvent implements ITsapiEvent {
	int eventPackage = 0;
	int cause = 0;
	int metaCode = 0;
	boolean newMetaEvent = false;

	protected Object privateData = null;

	public final int getCause() {
		if ((this.cause == 101) || (this.cause == 102) || (this.cause == 103)
				|| (this.cause == 104) || (this.cause == 105)
				|| (this.cause == 106) || (this.cause == 107)
				|| (this.cause == 108) || (this.cause == 109)
				|| (this.cause == 110)) {
			return this.cause;
		}
		return 100;
	}

	public final int getMetaCode() {
		return this.metaCode;
	}

	public final boolean isNewMetaEvent() {
		return this.newMetaEvent;
	}

	public final Object getPrivateData() {
		if (((this.privateData instanceof TsapiPrivate))
				|| ((this.privateData instanceof LucentChargeAdviceEvent))
				|| ((this.privateData instanceof TsapiPrivateStateEvent))) {
			return this.privateData;
		}
		return null;
	}

	public Object getObserved() {
		return null;
	}

	public final int getCallControlCause() {
		if ((this.cause == 101) || (this.cause == 202) || (this.cause == 203)
				|| (this.cause == 204) || (this.cause == 205)
				|| (this.cause == 206) || (this.cause == 207)
				|| (this.cause == 208) || (this.cause == 209)
				|| (this.cause == 210) || (this.cause == 211)
				|| (this.cause == 212) || (this.cause == 213)
				|| (this.cause == 214)) {
			return this.cause;
		}
		return 100;
	}

	public final int getCallCenterCause() {
		if ((this.cause == 101) || (this.cause == 302)) {
			return this.cause;
		}
		return 100;
	}

	public final int getMediaCause() {
		if (this.cause == 401) {
			return this.cause;
		}
		return 400;
	}

	public final int getEventPackage() {
		return this.eventPackage;
	}

	public final void setNewMetaEventFlag() {
		this.newMetaEvent = true;
	}

	public TsapiObserverEvent(int _cause, int _metaCode, Object _privateData,
			int _eventPackage) {
		this.cause = _cause;
		this.metaCode = _metaCode;
		this.privateData = _privateData;
		this.eventPackage = _eventPackage;
	}
}