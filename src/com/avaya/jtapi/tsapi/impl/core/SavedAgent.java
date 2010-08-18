package com.avaya.jtapi.tsapi.impl.core;

final class SavedAgent {
	TSAgent agent;
	long saveTime;

	SavedAgent(TSAgent _agent) {
		agent = _agent;
		saveTime = System.currentTimeMillis();
	}
}

