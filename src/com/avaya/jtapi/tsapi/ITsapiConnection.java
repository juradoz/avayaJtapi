package com.avaya.jtapi.tsapi;

import javax.telephony.Connection;
import javax.telephony.callcontrol.CallControlConnection;

public abstract interface ITsapiConnection extends Connection,
		CallControlConnection {
}

