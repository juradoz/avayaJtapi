 package com.avaya.jtapi.tsapi.impl.events.call;
 
 import com.avaya.jtapi.tsapi.ITsapiCallEvent;
 import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
 import com.avaya.jtapi.tsapi.TsapiPrivate;
 import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;
 import javax.telephony.Call;
 import javax.telephony.CallEvent;
 import javax.telephony.MetaEvent;
 import javax.telephony.privatedata.PrivateDataEvent;
 
 public class CallEventImpl
   implements CallEvent, PrivateDataEvent, ITsapiCallEvent
 {
   protected CallEventParams callEventParams;
   private MetaEvent metaEvent;
   private int id;
 
   public CallEventImpl(CallEventParams params, MetaEvent event, int eventId)
   {
     this.callEventParams = params;
     this.metaEvent = event;
     this.id = eventId;
   }
 
   public Call getCall()
   {
     return this.callEventParams.getCall();
   }
 
   public int getID()
   {
     return this.id;
   }
 
   public MetaEvent getMetaEvent()
   {
     return this.metaEvent;
   }
 
   public Object getSource()
   {
     return this.callEventParams.getCall();
   }
 
   public int getCause() {
     return this.callEventParams.getCause();
   }
 
   public Object getPrivateData() {
     Object privateData = this.callEventParams.getPrivateData();
     if ((privateData instanceof TsapiPrivate) || (privateData instanceof LucentChargeAdviceEvent) || (privateData instanceof TsapiPrivateStateEvent)) {
       return privateData;
     }
     return null;
   }
 
   public short getCSTACause() {
     return this.callEventParams.getCstaCause();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl
 * JD-Core Version:    0.5.4
 */