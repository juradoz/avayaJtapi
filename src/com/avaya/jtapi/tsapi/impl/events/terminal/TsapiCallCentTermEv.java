package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;

public abstract class TsapiCallCentTermEv extends TsapiTerminalEvent {
	public TsapiCallCentTermEv(final Terminal _device, final int _cause,
			final int _metaCode, final Object _privateData) {
		super(_device, _cause, _metaCode, _privateData, 2);
	}
}
