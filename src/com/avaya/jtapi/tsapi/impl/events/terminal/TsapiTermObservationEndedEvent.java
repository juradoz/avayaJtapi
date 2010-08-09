 package com.avaya.jtapi.tsapi.impl.events.terminal;
 
 import javax.telephony.Terminal;
 import javax.telephony.events.TermObservationEndedEv;
 
 public final class TsapiTermObservationEndedEvent extends TsapiTerminalEvent
   implements TermObservationEndedEv
 {
   public int getID()
   {
     return 121;
   }
 
   public TsapiTermObservationEndedEvent(Terminal _terminal, int _cause, Object _privateData)
   {
     super(_terminal, _cause, 136, _privateData);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermObservationEndedEvent
 * JD-Core Version:    0.5.4
 */