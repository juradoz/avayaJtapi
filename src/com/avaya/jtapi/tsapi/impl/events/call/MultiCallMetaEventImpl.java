 package com.avaya.jtapi.tsapi.impl.events.call;
 
 import java.util.ArrayList;
 import javax.telephony.Call;
 import javax.telephony.MetaEvent;
 import javax.telephony.MultiCallMetaEvent;
 
 public class MultiCallMetaEventImpl
   implements MultiCallMetaEvent
 {
   private CallEventParams callEventParams;
   private int id;
 
   public MultiCallMetaEventImpl(CallEventParams params, int id)
   {
     this.callEventParams = params;
     this.id = id;
   }
 
   public Call getNewCall()
   {
     return this.callEventParams.getCall();
   }
 
   public Call[] getOldCalls()
   {
     if (this.callEventParams.getOldCalls() == null)
       return null;
     Call[] oldCallArray = new Call[this.callEventParams.getOldCalls().size()];
     return (Call[])this.callEventParams.getOldCalls().toArray(oldCallArray);
   }
 
   public int getCause()
   {
     return this.callEventParams.getCause();
   }
 
   public int getID()
   {
     return this.id;
   }
 
   public MetaEvent getMetaEvent()
   {
     return null;
   }
 
   public Object getSource()
   {
     return this.callEventParams.getCall();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.MultiCallMetaEventImpl
 * JD-Core Version:    0.5.4
 */