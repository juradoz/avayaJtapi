 package com.avaya.jtapi.tsapi.impl.events.addr;
 
 import javax.telephony.MetaEvent;
 import javax.telephony.callcontrol.CallControlForwarding;
 
 public class AddressEventParams
 {
   CallControlForwarding[] callControlForwarding;
   boolean doNotDisturbState;
   int mwBits;
   int eventId;
   MetaEvent metaEvent;
   int cause;
   Object source;
   private Object privateData;
 
   public AddressEventParams()
   {
     this.mwBits = 0;
   }
 
   public Object getSource()
   {
     return this.source;
   }
 
   public void setSource(Object source) {
     this.source = source;
   }
 
   public void setMetaEvent(MetaEvent metaEvent) {
     this.metaEvent = metaEvent;
   }
 
   public int getEventId() {
     return this.eventId;
   }
 
   public void setEventId(int eventId) {
     this.eventId = eventId;
   }
 
   public int getCause() {
     return this.cause;
   }
 
   public void setCause(int cause) {
     this.cause = cause;
   }
 
   public MetaEvent getMetaEvent() {
     return this.metaEvent;
   }
 
   public CallControlForwarding[] getCallControlForwarding()
   {
     return this.callControlForwarding;
   }
 
   public void setCallControlForwarding(CallControlForwarding[] callControlForwarding) {
     this.callControlForwarding = callControlForwarding;
   }
 
   public boolean isDoNotDisturbState() {
     return this.doNotDisturbState;
   }
 
   public void setDoNotDisturbState(boolean doNotDisturbState) {
     this.doNotDisturbState = doNotDisturbState;
   }
 
   public int getMwBits() {
     return this.mwBits;
   }
 
   public void setMwBits(int mwBits) {
     this.mwBits = mwBits;
   }
   public Object getPrivateData() {
     return this.privateData;
   }
   public void setPrivateData(Object privateData) {
     this.privateData = privateData;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.AddressEventParams
 * JD-Core Version:    0.5.4
 */