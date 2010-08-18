package com.avaya.jtapi.tsapi;

import javax.telephony.TerminalConnection;
import javax.telephony.callcontrol.CallControlTerminalConnection;

public abstract interface ITsapiTerminalConnection extends TerminalConnection,
		CallControlTerminalConnection {
}

