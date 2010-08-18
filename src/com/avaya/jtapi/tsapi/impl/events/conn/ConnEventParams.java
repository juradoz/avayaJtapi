package com.avaya.jtapi.tsapi.impl.events.conn;

import javax.telephony.Connection;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;

public class ConnEventParams extends CallEventParams {
	private Connection connection;

	public ConnEventParams() {
		connection = null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}

