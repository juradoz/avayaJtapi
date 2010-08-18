package com.avaya.jtapi.tsapi.impl.core;

final class SavedConn {
	TSConnection conn;
	long saveTime;

	SavedConn(TSConnection _conn) {
		conn = _conn;
		saveTime = System.currentTimeMillis();
	}
}

