 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;
 import javax.telephony.Connection;
 
 public abstract class TsapiConnEvent extends TsapiCallEvent
 {
   public final Connection getConnection()
   {
     return ((ConnEventParams)this.params).getConnection();
   }
 
   public TsapiConnEvent(ConnEventParams params, int eventPackage)
   {
     super(params, eventPackage);
   }
 
   public TsapiConnEvent(ConnEventParams params)
   {
     this(params, 0);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnEvent
 * JD-Core Version:    0.5.4
 */