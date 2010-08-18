package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.acs.ACSRequestPrivilegesConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class RequestPrivilegesConfHandler implements ConfHandler {
	TSProviderImpl provider;
	String nonce;

	RequestPrivilegesConfHandler(TSProviderImpl provider) {
		this.provider = provider;
	}

	public String get_nonce() {
		return nonce;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (!(event.getEvent() instanceof ACSRequestPrivilegesConfEvent))) {
			return;
		}
		nonce = ((ACSRequestPrivilegesConfEvent) event.getEvent()).get_nonce();
	}
}
