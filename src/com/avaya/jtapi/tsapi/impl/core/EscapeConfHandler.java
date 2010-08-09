package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvcConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

class EscapeConfHandler implements ConfHandler {
	TSProviderImpl prov;
	ConfHandler extraHandler;
	protected Object privateData;

	EscapeConfHandler(TSProviderImpl _prov, ConfHandler _extraHandler) {
		prov = _prov;
		extraHandler = _extraHandler;
	}

	public Object getPrivateData() {
		return privateData;
	}

	public void handleConf(CSTAEvent event) {
		Object privData = null;
		try {
			if ((event == null)
					|| (!(event.getEvent() instanceof CSTAEscapeSvcConfEvent))) {
				return;
			}

			privData = event.getPrivData();
			prov.replyPriv = privData;
			privateData = privData;
		} finally {
			if (extraHandler != null) {
				extraHandler.handleConf(event);
			}
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.EscapeConfHandler JD-Core Version: 0.5.4
 */