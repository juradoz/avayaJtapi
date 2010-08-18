package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;

class TsapiHandshakeCompletedListener implements HandshakeCompletedListener {
	private HandshakeCompletedEvent event;

	TsapiHandshakeCompletedListener() {
		event = null;
	}

	SSLSession getSslSession() {
		if (event != null)
			return event.getSession();

		return null;
	}

	public void handshakeCompleted(final HandshakeCompletedEvent event) {
		this.event = event;

		synchronized (this) {
			super.notify();
		}
	}
}
