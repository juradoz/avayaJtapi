package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.acs.ACSSetPrivilegesConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class SetPrivilegesConfHandler implements ConfHandler {
	TSProviderImpl provider;

	SetPrivilegesConfHandler(TSProviderImpl provider) {
		this.provider = provider;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof ACSSetPrivilegesConfEvent))) {
			return;
		}

		this.provider.setCapabilities(this.provider.getCaps());
	}
}