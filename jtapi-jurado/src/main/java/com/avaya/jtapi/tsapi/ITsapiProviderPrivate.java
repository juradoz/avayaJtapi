package com.avaya.jtapi.tsapi;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.Connection;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;

public abstract interface ITsapiProviderPrivate {
	public abstract Call getCall(int paramInt);

	public abstract Address getAddress(ExtendedDeviceID paramExtendedDeviceID);

	public abstract Terminal getTerminal(ExtendedDeviceID paramExtendedDeviceID);

	public abstract Connection getConnection(ConnectionID paramConnectionID,
			Address paramAddress);

	public abstract TerminalConnection getTerminalConnection(
			ConnectionID paramConnectionID, Terminal paramTerminal);
}