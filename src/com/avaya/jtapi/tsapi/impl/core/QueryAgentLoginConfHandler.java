package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class QueryAgentLoginConfHandler implements ConfHandler {
	TSDevice device;

	QueryAgentLoginConfHandler(TSDevice _device) {
		this.device = _device;
	}

	public void handleConf(CSTAEvent event) {
		if ((event == null)
				|| (event.getPrivData() == null)
				|| (!(event.getPrivData() instanceof LucentQueryAgentLoginConfEvent))) {
			return;
		}
		int xrefID = ((LucentQueryAgentLoginConfEvent) event.getPrivData())
				.getPrivEventCrossRefID();
		this.device.getTSProviderImpl().addPrivateXref(xrefID, this.device);
	}
}