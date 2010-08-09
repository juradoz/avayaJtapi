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
		if ((event != null)
				&& (event.getEvent() instanceof ACSSetPrivilegesConfEvent)) {
			return;
		}
		return;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.SetPrivilegesConfHandler JD-Core Version:
 * 0.5.4
 */