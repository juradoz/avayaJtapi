 package com.avaya.jtapi.tsapi.impl.events;
 
 import javax.telephony.MetaEvent;
 import javax.telephony.callcontrol.CallControlEvent;
 
 public abstract class TsapiListenerCallControlEvent extends TsapiListenerEvent
   implements CallControlEvent
 {
   public TsapiListenerCallControlEvent(int eventId, int _cause, MetaEvent metaEvent, Object source, Object privateData)
   {
     super(eventId, _cause, metaEvent, source, privateData);
   }
 
   public int getCallControlCause()
   {
     if ((this.cause == 101) || (this.cause == 202) || (this.cause == 203) || (this.cause == 204) || (this.cause == 205) || (this.cause == 206) || (this.cause == 207) || (this.cause == 208) || (this.cause == 209) || (this.cause == 210) || (this.cause == 211) || (this.cause == 212) || (this.cause == 213) || (this.cause == 214))
     {
       return this.cause;
     }
     return 100;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallControlEvent
 * JD-Core Version:    0.5.4
 */