package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class QueryAgentLoginConfHandler implements ConfHandler {
	TSDevice device;

	QueryAgentLoginConfHandler(TSDevice _device) {
		device = _device;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (event.getPrivData() == null)
				|| (!(event.getPrivData() instanceof LucentQueryAgentLoginConfEvent))) {
			return;
		}
		int xrefID = ((LucentQueryAgentLoginConfEvent) event.getPrivData())
				.getPrivEventCrossRefID();
		device.getTSProviderImpl().addPrivateXref(xrefID, device);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.core.QueryAgentLoginConfHandler JD-Core Version:
 * 0.5.4
 */