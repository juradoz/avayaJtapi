 package com.avaya.jtapi.tsapi.impl.events.termConn;
 
 import javax.telephony.TerminalConnection;

import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;
 
 public final class TsapiTermConnDTMFEvent extends TsapiCallEvent
   
 {
   private char character;
 
   public int getID()
   {
     return 401;
   }
 
   public char getDtmfDigit()
   {
     return this.character;
   }
 
   public TerminalConnection getTerminalConnection()
   {
     return null;
   }
 
   public TsapiTermConnDTMFEvent(CallEventParams params, char _character)
   {
     super(params, 3);
     this.character = _character;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDTMFEvent
 * JD-Core Version:    0.5.4
 */