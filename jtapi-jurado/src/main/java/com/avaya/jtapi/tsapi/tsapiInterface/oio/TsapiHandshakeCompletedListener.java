package com.avaya.jtapi.tsapi.tsapiInterface.oio;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;

class TsapiHandshakeCompletedListener implements HandshakeCompletedListener {
	private HandshakeCompletedEvent event = null;

	public void handshakeCompleted(HandshakeCompletedEvent event) {
		this.event = event;

		synchronized (this) {
			notify();
		}
	}

	SSLSession getSslSession() {
		if (this.event != null) {
			return this.event.getSession();
		}

		return null;
	}
}