package com.avaya.jtapi.tsapi.adapters;

import javax.telephony.callcontrol.CallControlTerminalEvent;
import javax.telephony.callcontrol.CallControlTerminalListener;

public abstract class CallControlTerminalListenerAdapter extends TerminalListenerAdapter
  implements CallControlTerminalListener
{
  public void terminalDoNotDisturb(CallControlTerminalEvent event)
  {
  }
}

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.adapters.CallControlTerminalListenerAdapter
 * JD-Core Version:    0.5.4
 */