package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginConfEvent;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;

final class QueryAgentLoginConfHandler implements ConfHandler {
	TSDevice device;

	QueryAgentLoginConfHandler(final TSDevice _device) {
		device = _device;
	}

	public void handleConf(final CSTAEvent event) {
		if (event == null
				|| event.getPrivData() == null
				|| !(event.getPrivData() instanceof LucentQueryAgentLoginConfEvent))
			return;
		final int xrefID = ((LucentQueryAgentLoginConfEvent) event
				.getPrivData()).getPrivEventCrossRefID();
		device.getTSProviderImpl().addPrivateXref(xrefID, device);
	}
}
