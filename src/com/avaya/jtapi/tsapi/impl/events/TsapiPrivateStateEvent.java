package com.avaya.jtapi.tsapi.impl.events;

public abstract class TsapiPrivateStateEvent {
	int tsapiState;

	protected TsapiPrivateStateEvent(final int _tsapiState) {
		tsapiState = _tsapiState;
	}

	public int getTsapiState() {
		return tsapiState;
	}
}
