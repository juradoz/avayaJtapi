 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import javax.telephony.events.ConnInProgressEv;
 
 public final class TsapiConnInProgressEvent extends TsapiConnEvent
   implements ConnInProgressEv
 {
   public int getID()
   {
     return 109;
   }
 
   public TsapiConnInProgressEvent(ConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnInProgressEvent
 * JD-Core Version:    0.5.4
 */