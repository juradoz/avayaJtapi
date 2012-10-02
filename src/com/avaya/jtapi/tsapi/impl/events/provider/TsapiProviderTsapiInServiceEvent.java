package com.avaya.jtapi.tsapi.impl.events.provider;

import com.avaya.jtapi.tsapi.ITsapiProviderTsapiInServiceEvent;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;

public final class TsapiProviderTsapiInServiceEvent extends
		TsapiPrivateStateEvent implements ITsapiProviderTsapiInServiceEvent {
	public TsapiProviderTsapiInServiceEvent() {
		super(2);
	}
}