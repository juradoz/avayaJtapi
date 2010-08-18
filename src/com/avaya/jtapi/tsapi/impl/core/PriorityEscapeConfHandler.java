package com.avaya.jtapi.tsapi.impl.core;

import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;

final class PriorityEscapeConfHandler extends EscapeConfHandler implements
		HandleConfOnCurrentThread {
	PriorityEscapeConfHandler(TSProviderImpl _prov, ConfHandler _extraHandler) {
		super(_prov, _extraHandler);
	}
}
