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
	private final TsapiInvokeIDTable invokeTable;
	private String debugID;

	public TsapiEventDistributor(final TsapiInvokeIDTable _invokeTable,
			final String _debugID) {
		invokeTable = _invokeTable;
	}

	@Override
	public void close() {
	}

	@Override
	public void handleEvent(final CSTAEvent event) {
		try {
			final long begin = System.currentTimeMillis();

			CSTAPrivate.translatePrivateData(event, debugID);

			switch (event.getEventHeader().getEventClass()) {
			case 2:
				final ACSConfirmation acsConf = (ACSConfirmation) event
						.getEvent();
				TSInvokeID invokeID = invokeTable.getTSInvokeID(acsConf
						.getInvokeID());
				if (invokeID != null) {
					invokeTable.deallocTSInvokeID(invokeID);
					invokeID.setConf(event);
				}
				break;
			case 5:
				final CSTAConfirmation cstaConf = (CSTAConfirmation) event
						.getEvent();
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
				TsapiEventDistributor.log
						.info("DISTRIBUTE thread: WARNING: bad event in TSDistributeCstaEventThread");
			}

			final long end = System.currentTimeMillis();
			final long delay = end - begin;

			final long threshold = CSTATSProvider
					.getHandleCSTAEventTimeThreshold();
			if (threshold > 0L && delay > threshold)
				TsapiEventDistributor.log
						.info("TsapiEventDistributor.handleEvent(): exceeded threshold ("
								+ threshold
								+ ") for event "
								+ event
								+ ": "
								+ delay);

		} catch (final Exception e) {
			if (handler == null) {
				TsapiEventDistributor.log
						.error("TsapiSession: no handler when Exception received."
								+ e);

				TsapiEventDistributor.log.error(e.getMessage(), e);
				return;
			}
			handler.eventDistributorException(e);
		}
	}

	@Override
	public synchronized void setUnsolicitedHandler(
			final TsapiUnsolicitedHandler handler) {
		this.handler = handler;
	}
}
