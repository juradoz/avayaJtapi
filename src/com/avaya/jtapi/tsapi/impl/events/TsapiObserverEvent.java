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

	public TsapiObserverEvent(int _cause, int _metaCode, Object _privateData,
			int _eventPackage) {
		cause = _cause;
		metaCode = _metaCode;
		privateData = _privateData;
		eventPackage = _eventPackage;
	}

	public final int getCallCenterCause() {
		if ((cause == 101) || (cause == 302)) {
			return cause;
		}
		return 100;
	}

	public final int getCallControlCause() {
		if ((cause == 101) || (cause == 202) || (cause == 203)
				|| (cause == 204) || (cause == 205) || (cause == 206)
				|| (cause == 207) || (cause == 208) || (cause == 209)
				|| (cause == 210) || (cause == 211) || (cause == 212)
				|| (cause == 213) || (cause == 214)) {
			return cause;
		}
		return 100;
	}

	public final int getCause() {
		if ((cause == 101) || (cause == 102) || (cause == 103)
				|| (cause == 104) || (cause == 105) || (cause == 106)
				|| (cause == 107) || (cause == 108) || (cause == 109)
				|| (cause == 110)) {
			return cause;
		}
		return 100;
	}

	public final int getEventPackage() {
		return eventPackage;
	}

	public final int getMediaCause() {
		if (cause == 401) {
			return cause;
		}
		return 400;
	}

	public final int getMetaCode() {
		return metaCode;
	}

	public Object getObserved() {
		return null;
	}

	public final Object getPrivateData() {
		if ((privateData instanceof TsapiPrivate)
				|| (privateData instanceof LucentChargeAdviceEvent)
				|| (privateData instanceof TsapiPrivateStateEvent)) {
			return privateData;
		}
		return null;
	}

	public final boolean isNewMetaEvent() {
		return newMetaEvent;
	}

	public final void setNewMetaEventFlag() {
		newMetaEvent = true;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent JD-Core Version: 0.5.4
 */