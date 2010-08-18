package com.avaya.jtapi.tsapi.impl.events.provider;

import com.avaya.jtapi.tsapi.ITsapiProviderTsapiOutOfServiceEvent;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;

public final class TsapiProviderTsapiOutOfServiceEvent extends
		TsapiPrivateStateEvent implements ITsapiProviderTsapiOutOfServiceEvent {
	public TsapiProviderTsapiOutOfServiceEvent() {
		super(0);
	}
}

