 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import javax.telephony.events.ConnFailedEv;
 
 public final class TsapiConnFailedEvent extends TsapiConnEvent
   implements ConnFailedEv
 {
   public int getID()
   {
     return 108;
   }
 
   public TsapiConnFailedEvent(ConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnFailedEvent
 * JD-Core Version:    0.5.4
 */