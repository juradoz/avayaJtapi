 package com.avaya.jtapi.tsapi.impl.events.terminal;
 
 import javax.telephony.Terminal;
 
 public abstract class TsapiCallCtlTerminalEvent extends TsapiTerminalEvent
 {
   public TsapiCallCtlTerminalEvent(Terminal _device, int _cause, int _metaCode, Object _privateData)
   {
     super(_device, _cause, _metaCode, _privateData, 1);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TsapiCallCtlTerminalEvent
 * JD-Core Version:    0.5.4
 */