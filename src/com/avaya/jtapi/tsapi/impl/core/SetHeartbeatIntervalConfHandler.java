package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.acs.ACSSetHeartbeatIntervalConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class SetHeartbeatIntervalConfHandler implements ConfHandler {
	TSProviderImpl provider;

	SetHeartbeatIntervalConfHandler(final TSProviderImpl provider) {
		this.provider = provider;
	}

	public void handleConf(final CSTAEvent event) {
		if (event == null
				|| !(event.getEvent() instanceof ACSSetHeartbeatIntervalConfEvent))
			return;

		provider
				.setClientHeartbeatInterval(((ACSSetHeartbeatIntervalConfEvent) event
						.getEvent()).getHeartbeatInterval());
	}
}
