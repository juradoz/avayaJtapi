package com.avaya.jtapi.tsapi.tsapiInterface;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.acs.ACSConfirmation;
import com.avaya.jtapi.tsapi.csta1.CSTAConfirmation;
import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
import com.avaya.jtapi.tsapi.csta1.CSTATSProvider;

public class TsapiEventDistributor implements TsapiEventHandler {
	private static Logger log = Logger.getLogger(TsapiEventDistributor.class);
	private TsapiUnsolicitedHandler handler;
	private TsapiInvokeIDTable invokeTable;
	private String debugID;

	public TsapiEventDistributor(TsapiInvokeIDTable _invokeTable,
			String _debugID) {
		invokeTable = _invokeTable;
	}

	public void close() {
	}

	public void handleEvent(CSTAEvent event) {
		try {
			long begin = System.currentTimeMillis();

			CSTAPrivate.translatePrivateData(event, debugID);

			switch (event.getEventHeader().getEventClass()) {
			case 2:
				ACSConfirmation acsConf = (ACSConfirmation) event.getEvent();
				TSInvokeID invokeID = invokeTable.getTSInvokeID(acsConf
						.getInvokeID());
				if (invokeID != null) {
					invokeTable.deallocTSInvokeID(invokeID);
					invokeID.setConf(event);
				}
				break;
			case 5:
				CSTAConfirmation cstaConf = (CSTAConfirmation) event.getEvent();
				invokeID = invokeTable.getTSInvokeID(cstaConf.getInvokeID());
				if (invokeID != null) {
					invokeTable.deallocTSInvokeID(invokeID);
					invokeID.setConf(event);
				}
				break;
			case 1:
				handler.acsUnsolicited(event);
				break;
			case 4:
				handler.cstaUnsolicited(event);
				break;
			case 3:
				handler.cstaRequest(event);
				break;
			case 6:
				handler.cstaEventReport(event);
				break;
			default:
				log
						.info("DISTRIBUTE thread: WARNING: bad event in TSDistributeCstaEventThread");
			}

			long end = System.currentTimeMillis();
			long delay = end - begin;

			long threshold = CSTATSProvider.getHandleCSTAEventTimeThreshold();
			if ((threshold > 0L) && (delay > threshold)) {
				log
						.info("TsapiEventDistributor.handleEvent(): exceeded threshold ("
								+ threshold
								+ ") for event "
								+ event
								+ ": "
								+ delay);
			}

		} catch (Exception e) {
			if (handler == null) {
				log.error("TsapiSession: no handler when Exception received."
						+ e);

				log.error(e.getMessage(), e);
				return;
			}
			handler.eventDistributorException(e);
		}
	}

	public synchronized void setUnsolicitedHandler(
			TsapiUnsolicitedHandler handler) {
		this.handler = handler;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.TsapiEventDistributor JD-Core Version:
 * 0.5.4
 */