package com.avaya.jtapi.tsapi;

import javax.telephony.Terminal;
import javax.telephony.callcenter.AgentTerminal;
import javax.telephony.callcontrol.CallControlTerminal;

public abstract interface ITsapiTerminal extends Terminal, CallControlTerminal,
		AgentTerminal {
}
