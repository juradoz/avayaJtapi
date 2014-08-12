package com.avaya.jtapi.tsapi.impl.events.provider;

import com.avaya.jtapi.tsapi.ITsapiProviderTsapiInitializingEvent;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;

public final class TsapiProviderTsapiInitializingEvent extends
		TsapiPrivateStateEvent implements ITsapiProviderTsapiInitializingEvent {
	public TsapiProviderTsapiInitializingEvent() {
		super(1);
	}
}