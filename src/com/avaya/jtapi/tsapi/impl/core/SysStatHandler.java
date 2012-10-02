package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatReqConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatStartConfEvent;
import com.avaya.jtapi.tsapi.csta1.CSTASysStatStopConfEvent;
import com.avaya.jtapi.tsapi.csta1.LinkState;
import com.avaya.jtapi.tsapi.csta1.LucentLinkStatusEvent;
import com.avaya.jtapi.tsapi.csta1.SystemStatusFilter;
import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;
import org.apache.log4j.Logger;

final class SysStatHandler implements ConfHandler, HandleConfOnCurrentThread {
	private static final Logger log = Logger.getLogger(SysStatHandler.class);
	private short systemStatus;

	public void handleConf(CSTAEvent event) {
		if (event == null) {
			return;
		}
		if ((event.getEvent() instanceof CSTASysStatStartConfEvent)) {
			log.info("Filter set at "
					+ SystemStatusFilter.print(
							((CSTASysStatStartConfEvent) event.getEvent())
									.getFilter(), "", ""));

			if ((event.getPrivData() instanceof LucentLinkStatusEvent)) {
				short state = ((LucentLinkStatusEvent) event.getPrivData())
						.getLinkStatus().getLinkState();
				log.info("Link state is " + LinkState.print(state, "", ""));
			}
		} else if ((event.getEvent() instanceof CSTASysStatStopConfEvent)) {
			log.info("Event monitoring was ended successfully");
		} else if ((event.getEvent() instanceof CSTASysStatReqConfEvent)) {
			this.systemStatus = ((CSTASysStatReqConfEvent) event.getEvent())
					.getSystemStatus();
		}
	}

	public short getSystemStatus() {
		return this.systemStatus;
	}

}