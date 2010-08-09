 package com.avaya.jtapi.tsapi.impl.events.termConn;
 
 import com.avaya.jtapi.tsapi.ITsapiCallInfo;
 import javax.telephony.callcontrol.events.CallCtlTermConnHeldEv;
 
 public class TsapiTermConnHeldEvent extends TsapiCallCtlTermConnEvent
   implements CallCtlTermConnHeldEv, ITsapiCallInfo
 {
   public final int getID()
   {
     return 216;
   }
 
   public TsapiTermConnHeldEvent(TermConnEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnHeldEvent
 * JD-Core Version:    0.5.4
 */