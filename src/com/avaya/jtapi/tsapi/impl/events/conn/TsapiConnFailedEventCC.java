 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import com.avaya.jtapi.tsapi.ITsapiCallInfo;
 import javax.telephony.callcontrol.events.CallCtlConnFailedEv;
 
 public class TsapiConnFailedEventCC extends TsapiCallCtlConnEvent
   implements CallCtlConnFailedEv, ITsapiCallInfo
 {
   public final int getID()
   {
     return 207;
   }
 
   public TsapiConnFailedEventCC(ConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnFailedEventCC
 * JD-Core Version:    0.5.4
 */