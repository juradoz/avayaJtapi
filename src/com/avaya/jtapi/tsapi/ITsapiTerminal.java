package com.avaya.jtapi.tsapi;

import javax.telephony.Terminal;
import javax.telephony.callcenter.AgentTerminal;
import javax.telephony.callcontrol.CallControlTerminal;

public abstract interface ITsapiTerminal extends Terminal, CallControlTerminal,
		AgentTerminal {
}

/*
 * Location: C:\Documents and Settings\Daniel Jurado\Meus documentos\My
 * Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar Qualified Name:
 * com.avaya.jtapi.tsapi.ITsapiTerminal JD-Core Version: 0.5.4
 */