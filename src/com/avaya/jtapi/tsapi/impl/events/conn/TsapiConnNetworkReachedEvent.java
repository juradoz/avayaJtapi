 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import com.avaya.jtapi.tsapi.ITsapiCallInfo;
 import com.avaya.jtapi.tsapi.ITsapiConnNetworkReachedEvent;
 import com.avaya.jtapi.tsapi.NetworkProgressInfo;
 
 public class TsapiConnNetworkReachedEvent extends TsapiCallCtlConnEvent
   implements ITsapiConnNetworkReachedEvent, ITsapiCallInfo
 {
   public final NetworkProgressInfo getNetworkProgressInfo()
   {
     if (this.privateData instanceof NetworkProgressInfo) {
       return (NetworkProgressInfo)this.privateData;
     }
     return null;
   }
 
   public final int getID()
   {
     return 210;
   }
 
   public TsapiConnNetworkReachedEvent(ConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkReachedEvent
 * JD-Core Version:    0.5.4
 */