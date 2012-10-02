package com.avaya.jtapi.tsapi.tsapiInterface;

import com.avaya.jtapi.tsapi.csta1.CSTAEvent;

public abstract interface ConfHandler {
	public abstract void handleConf(CSTAEvent paramCSTAEvent);
}