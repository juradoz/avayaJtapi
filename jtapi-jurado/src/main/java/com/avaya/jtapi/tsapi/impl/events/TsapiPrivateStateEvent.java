package com.avaya.jtapi.tsapi.impl.events;

public abstract class TsapiPrivateStateEvent {
	int tsapiState;

	public int getTsapiState() {
		return this.tsapiState;
	}

	protected TsapiPrivateStateEvent(int _tsapiState) {
		this.tsapiState = _tsapiState;
	}
}