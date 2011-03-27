package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.acs.ACSSetPrivilegesConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class SetPrivilegesConfHandler implements ConfHandler {
	TSProviderImpl provider;

	SetPrivilegesConfHandler(final TSProviderImpl provider) {
		this.provider = provider;
	}

	@Override
	public void handleConf(final CSTAEvent event) {
		if (event != null
				&& event.getEvent() instanceof ACSSetPrivilegesConfEvent)
			return;
		return;
	}
}
