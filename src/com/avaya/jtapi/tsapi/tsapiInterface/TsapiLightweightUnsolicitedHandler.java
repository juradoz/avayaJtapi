package com.avaya.jtapi.tsapi.tsapiInterface;

import java.net.SocketException;

import org.apache.log4j.Logger;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;

public class TsapiLightweightUnsolicitedHandler implements
		TsapiUnsolicitedHandler {
	private static Logger log = Logger
			.getLogger(TsapiLightweightUnsolicitedHandler.class);
	private final TsapiSession m_session;

	public TsapiLightweightUnsolicitedHandler(final TsapiSession sess) {
		m_session = sess;
	}

	@Override
	public void acsUnsolicited(final CSTAEvent event) {
		TsapiLightweightUnsolicitedHandler.log
				.info("TsapiLightweightUnsolicitedHandler acsUnsolicited saw unexpected event "
						+ event);
	}

	@Override
	public void cstaEventReport(final CSTAEvent event) {
		TsapiLightweightUnsolicitedHandler.log
				.info("TsapiLightweightUnsolicitedHandler cstaEventReport saw unexpected event "
						+ event);
	}

	@Override
	public void cstaRequest(final CSTAEvent event) {
		TsapiLightweightUnsolicitedHandler.log
				.info("TsapiLightweightUnsolicitedHandler cstaRequest saw unexpected event "
						+ event);
	}

	@Override
	public void cstaUnsolicited(final CSTAEvent event) {
		TsapiLightweightUnsolicitedHandler.log
				.info("TsapiLightweightUnsolicitedHandler cstaUnsolicited saw unexpected event "
						+ event);
	}

	@Override
	public void eventDistributorException(final Exception e) {
		if (e instanceof SocketException)
			TsapiLightweightUnsolicitedHandler.log
					.info("TsapiLightweightUnsolicitedHandler eventDistributorException: normal 'Socket closed'.");
		else {
			TsapiLightweightUnsolicitedHandler.log
					.error("TsapiLightweightUnsolicitedHandler eventDistributorException: unexpected exception received: "
							+ e);
			TsapiLightweightUnsolicitedHandler.log.error(e.getMessage(), e);
		}

		m_session.close();
	}
}
