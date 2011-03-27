package com.avaya.jtapi.tsapi.impl.core;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatReqConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatStartConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatStopConfEvent;
import com.avaya.jtapi.tsapi.csta1.LinkState;
import com.avaya.jtapi.tsapi.csta1.LucentLinkStatusEvent;
import com.avaya.jtapi.tsapi.csta1.SystemStatusFilter;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;

final class SysStatHandler implements ConfHandler, HandleConfOnCurrentThread {
	private static final Logger log = Logger.getLogger(SysStatHandler.class);
	private short systemStatus;

	public short getSystemStatus() {
		return systemStatus;
	}

	@Override
	public void handleConf(final CSTAEvent event) {
		if (event == null)
			return;
		if (event.getEvent() instanceof CSTASysStatStartConfEvent) {
			SysStatHandler.log.info("Filter set at "
					+ SystemStatusFilter.print(
							((CSTASysStatStartConfEvent) event.getEvent())
									.getFilter(), "", ""));

			if (event.getPrivData() instanceof LucentLinkStatusEvent) {
				final short state = ((LucentLinkStatusEvent) event
						.getPrivData()).getLinkStatus().getLinkState();
				SysStatHandler.log.info("Link state is "
						+ LinkState.print(state, "", ""));
			}
		} else if (event.getEvent() instanceof CSTASysStatStopConfEvent)
			SysStatHandler.log.info("Event monitoring was ended successfully");
		else if (event.getEvent() instanceof CSTASysStatReqConfEvent)
			systemStatus = ((CSTASysStatReqConfEvent) event.getEvent())
					.getSystemStatus();
	}
}
