/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.callcontrol.CallControlForwarding;
/*    */ 
/*    */ public class AddressEventParams
/*    */ {
/*    */   CallControlForwarding[] callControlForwarding;
/*    */   boolean doNotDisturbState;
/*    */   int mwBits;
/*    */   int eventId;
/*    */   MetaEvent metaEvent;
/*    */   int cause;
/*    */   Object source;
/*    */   private Object privateData;
/*    */ 
/*    */   public AddressEventParams()
/*    */   {
/* 10 */     this.mwBits = 0;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 20 */     return this.source;
/*    */   }
/*    */ 
/*    */   public void setSource(Object source) {
/* 24 */     this.source = source;
/*    */   }
/*    */ 
/*    */   public void setMetaEvent(MetaEvent metaEvent) {
/* 28 */     this.metaEvent = metaEvent;
/*    */   }
/*    */ 
/*    */   public int getEventId() {
/* 32 */     return this.eventId;
/*    */   }
/*    */ 
/*    */   public void setEventId(int eventId) {
/* 36 */     this.eventId = eventId;
/*    */   }
/*    */ 
/*    */   public int getCause() {
/* 40 */     return this.cause;
/*    */   }
/*    */ 
/*    */   public void setCause(int cause) {
/* 44 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   public MetaEvent getMetaEvent() {
/* 48 */     return this.metaEvent;
/*    */   }
/*    */ 
/*    */   public CallControlForwarding[] getCallControlForwarding()
/*    */   {
/* 53 */     return this.callControlForwarding;
/*    */   }
/*    */ 
/*    */   public void setCallControlForwarding(CallControlForwarding[] callControlForwarding) {
/* 57 */     this.callControlForwarding = callControlForwarding;
/*    */   }
/*    */ 
/*    */   public boolean isDoNotDisturbState() {
/* 61 */     return this.doNotDisturbState;
/*    */   }
/*    */ 
/*    */   public void setDoNotDisturbState(boolean doNotDisturbState) {
/* 65 */     this.doNotDisturbState = doNotDisturbState;
/*    */   }
/*    */ 
/*    */   public int getMwBits() {
/* 69 */     return this.mwBits;
/*    */   }
/*    */ 
/*    */   public void setMwBits(int mwBits) {
/* 73 */     this.mwBits = mwBits;
/*    */   }
/*    */   public Object getPrivateData() {
/* 76 */     return this.privateData;
/*    */   }
/*    */   public void setPrivateData(Object privateData) {
/* 79 */     this.privateData = privateData;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.AddressEventParams
 * JD-Core Version:    0.5.4
 */