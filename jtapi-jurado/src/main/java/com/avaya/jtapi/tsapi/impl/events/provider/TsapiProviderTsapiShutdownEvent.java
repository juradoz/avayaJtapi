package com.avaya.jtapi.tsapi.impl.events.provider;

import com.avaya.jtapi.tsapi.ITsapiProviderTsapiShutdownEvent;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;

public final class TsapiProviderTsapiShutdownEvent extends
		TsapiPrivateStateEvent implements ITsapiProviderTsapiShutdownEvent {
	public TsapiProviderTsapiShutdownEvent() {
		super(3);
	}
}