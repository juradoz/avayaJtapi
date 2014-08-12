package com.avaya.jtapi.tsapi.impl.core;

final class SavedConn {
	TSConnection conn;
	long saveTime;

	SavedConn(TSConnection _conn) {
		this.conn = _conn;
		this.saveTime = System.currentTimeMillis();
	}
}