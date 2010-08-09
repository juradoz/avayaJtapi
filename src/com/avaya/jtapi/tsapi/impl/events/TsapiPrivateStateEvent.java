package com.avaya.jtapi.tsapi.impl.events;

public abstract class TsapiPrivateStateEvent {
	int tsapiState;

	protected TsapiPrivateStateEvent(int _tsapiState) {
		tsapiState = _tsapiState;
	}

	public int getTsapiState() {
		return tsapiState;
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent JD-Core Version:
 * 0.5.4
 */