 package com.avaya.jtapi.tsapi.impl.events.call;
 
 import javax.telephony.events.CallInvalidEv;
 
 public final class TsapiCallInvalidEvent extends TsapiCallEvent
   implements CallInvalidEv
 {
   public int getID()
   {
     return 102;
   }
 
   public TsapiCallInvalidEvent(CallEventParams params)
   {
     super(params);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.TsapiCallInvalidEvent
 * JD-Core Version:    0.5.4
 */