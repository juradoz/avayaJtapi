package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
import java.net.SocketException;
import org.apache.log4j.Logger;

public class TsapiLightweightUnsolicitedHandler implements
		TsapiUnsolicitedHandler {
	private static Logger log = Logger
			.getLogger(TsapiLightweightUnsolicitedHandler.class);
	private TsapiSession m_session;

	public TsapiLightweightUnsolicitedHandler(TsapiSession sess) {
		this.m_session = sess;
	}

	public void acsUnsolicited(CSTAEvent event) {
		log.info("TsapiLightweightUnsolicitedHandler acsUnsolicited saw unexpected event "
				+ event);
	}

	public void cstaEventReport(CSTAEvent event) {
		log.info("TsapiLightweightUnsolicitedHandler cstaEventReport saw unexpected event "
				+ event);
	}

	public void cstaRequest(CSTAEvent event) {
		log.info("TsapiLightweightUnsolicitedHandler cstaRequest saw unexpected event "
				+ event);
	}

	public void cstaUnsolicited(CSTAEvent event) {
		log.info("TsapiLightweightUnsolicitedHandler cstaUnsolicited saw unexpected event "
				+ event);
	}

	public void eventDistributorException(Exception e) {
		if ((e instanceof SocketException)) {
			log.info("TsapiLightweightUnsolicitedHandler eventDistributorException: normal 'Socket closed'.");
		} else {
			log.error("TsapiLightweightUnsolicitedHandler eventDistributorException: unexpected exception received: "
					+ e);
			log.error(e.getMessage(), e);
		}

		this.m_session.close();
	}
}