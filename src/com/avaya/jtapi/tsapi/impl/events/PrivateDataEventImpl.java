 package com.avaya.jtapi.tsapi.impl.events;
 
 import javax.telephony.MetaEvent;
 import javax.telephony.privatedata.PrivateDataEvent;
 
 public class PrivateDataEventImpl extends TsapiListenerEvent
   implements PrivateDataEvent
 {
   public PrivateDataEventImpl(int eventId, int _cause, MetaEvent metaEvent, Object source, Object privateData)
   {
     super(eventId, _cause, metaEvent, source, privateData);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.PrivateDataEventImpl
 * JD-Core Version:    0.5.4
 */