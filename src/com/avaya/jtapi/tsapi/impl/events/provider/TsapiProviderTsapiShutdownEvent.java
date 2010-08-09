package com.avaya.jtapi.tsapi.impl.events.provider;

import com.avaya.jtapi.tsapi.ITsapiProviderTsapiShutdownEvent;
import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;

public final class TsapiProviderTsapiShutdownEvent extends
		TsapiPrivateStateEvent implements ITsapiProviderTsapiShutdownEvent {
	public TsapiProviderTsapiShutdownEvent() {
		super(3);
	}
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiShutdownEvent
 * JD-Core Version: 0.5.4
 */