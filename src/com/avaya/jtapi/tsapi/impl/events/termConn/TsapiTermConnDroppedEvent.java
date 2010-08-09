 package com.avaya.jtapi.tsapi.impl.events.termConn;
 
 import javax.telephony.events.TermConnDroppedEv;
 
 public final class TsapiTermConnDroppedEvent extends TsapiTermConnEvent
   implements TermConnDroppedEv
 {
   public int getID()
   {
     return 117;
   }
 
   public TsapiTermConnDroppedEvent(TermConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnDroppedEvent
 * JD-Core Version:    0.5.4
 */