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
		if (event != null) {
			return event.getSession();
		}

		return null;
	}

	public void handshakeCompleted(HandshakeCompletedEvent event) {
		this.event = event;

		synchronized (this) {
			super.notify();
		}
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiHandshakeCompletedListener
 * JD-Core Version: 0.5.4
 */