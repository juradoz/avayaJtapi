package com.avaya.jtapi.tsapi.impl.events.conn;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import javax.telephony.Connection;

public class ConnEventParams extends CallEventParams {
	private Connection connection = null;

	public Connection getConnection() {
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}