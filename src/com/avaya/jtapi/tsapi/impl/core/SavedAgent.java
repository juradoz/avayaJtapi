package com.avaya.jtapi.tsapi.impl.core;

final class SavedAgent {
	TSAgent agent;
	long saveTime;

	SavedAgent(TSAgent _agent) {
		this.agent = _agent;
		this.saveTime = System.currentTimeMillis();
	}
}