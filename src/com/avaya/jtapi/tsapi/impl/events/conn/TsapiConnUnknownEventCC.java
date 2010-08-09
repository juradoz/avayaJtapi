 package com.avaya.jtapi.tsapi.impl.events.conn;
 
 import com.avaya.jtapi.tsapi.ITsapiCallInfo;
 import javax.telephony.callcontrol.events.CallCtlConnUnknownEv;
 
 public class TsapiConnUnknownEventCC extends TsapiCallCtlConnEvent
   implements CallCtlConnUnknownEv, ITsapiCallInfo
 {
   public final int getID()
   {
     return 213;
   }
 
   public TsapiConnUnknownEventCC(ConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnUnknownEventCC
 * JD-Core Version:    0.5.4
 */