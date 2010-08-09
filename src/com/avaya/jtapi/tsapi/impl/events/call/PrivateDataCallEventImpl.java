 package com.avaya.jtapi.tsapi.impl.events.call;
 
 import javax.telephony.MetaEvent;
 import javax.telephony.privatedata.PrivateDataEvent;
 
 public class PrivateDataCallEventImpl
   implements PrivateDataEvent
 {
   private MetaEvent metaEvent;
   private Object privateData;
   private Object source;
   private int cause;
 
   public PrivateDataCallEventImpl(Object privateData, Object source, int cause, MetaEvent metaEvent)
   {
     this.privateData = privateData;
     this.source = source;
     this.cause = cause;
     this.metaEvent = metaEvent;
   }
 
   public Object getPrivateData()
   {
     return this.privateData;
   }
 
   public int getCause()
   {
     return this.cause;
   }
 
   public int getID()
   {
     return 601;
   }
 
   public MetaEvent getMetaEvent()
   {
     return this.metaEvent;
   }
 
   public Object getSource()
   {
     return this.source;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.PrivateDataCallEventImpl
 * JD-Core Version:    0.5.4
 */