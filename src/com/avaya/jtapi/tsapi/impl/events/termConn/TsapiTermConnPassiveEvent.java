 package com.avaya.jtapi.tsapi.impl.events.termConn;
 
 import javax.telephony.events.TermConnPassiveEv;
 
 public final class TsapiTermConnPassiveEvent extends TsapiTermConnEvent
   implements TermConnPassiveEv
 {
   public int getID()
   {
     return 118;
   }
 
   public TsapiTermConnPassiveEvent(TermConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnPassiveEvent
 * JD-Core Version:    0.5.4
 */