package com.avaya.jtapi.tsapi.tsapiInterface;

import java.util.EventListener;

public abstract interface ITsapiHeartbeatTimeoutListener extends EventListener {
	public abstract void heartbeatTimeout();
}