package com.avaya.jtapi.tsapi.impl.events.terminal;

import javax.telephony.Terminal;
import javax.telephony.events.TermObservationEndedEv;

public final class TsapiTermObservationEndedEvent extends TsapiTerminalEvent
		implements TermObservationEndedEv {
	public TsapiTermObservationEndedEvent(final Terminal _terminal,
			final int _cause, final Object _privateData) {
		super(_terminal, _cause, 136, _privateData);
	}

	@Override
	public int getID() {
		return 121;
	}
}
