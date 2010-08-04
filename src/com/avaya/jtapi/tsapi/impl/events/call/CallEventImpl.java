/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiCallEvent;
/*    */ import com.avaya.jtapi.tsapi.LucentChargeAdviceEvent;
/*    */ import com.avaya.jtapi.tsapi.TsapiPrivate;
/*    */ import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;
/*    */ import javax.telephony.Call;
/*    */ import javax.telephony.CallEvent;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.privatedata.PrivateDataEvent;
/*    */ 
/*    */ public class CallEventImpl
/*    */   implements CallEvent, PrivateDataEvent, ITsapiCallEvent
/*    */ {
/*    */   protected CallEventParams callEventParams;
/*    */   private MetaEvent metaEvent;
/*    */   private int id;
/*    */ 
/*    */   public CallEventImpl(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 27 */     this.callEventParams = params;
/* 28 */     this.metaEvent = event;
/* 29 */     this.id = eventId;
/*    */   }
/*    */ 
/*    */   public Call getCall()
/*    */   {
/* 36 */     return this.callEventParams.getCall();
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 43 */     return this.id;
/*    */   }
/*    */ 
/*    */   public MetaEvent getMetaEvent()
/*    */   {
/* 50 */     return this.metaEvent;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 57 */     return this.callEventParams.getCall();
/*    */   }
/*    */ 
/*    */   public int getCause() {
/* 61 */     return this.callEventParams.getCause();
/*    */   }
/*    */ 
/*    */   public Object getPrivateData() {
/* 65 */     Object privateData = this.callEventParams.getPrivateData();
/* 66 */     if ((privateData instanceof TsapiPrivate) || (privateData instanceof LucentChargeAdviceEvent) || (privateData instanceof TsapiPrivateStateEvent)) {
/* 67 */       return privateData;
/*    */     }
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */   public short getCSTACause() {
/* 73 */     return this.callEventParams.getCstaCause();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl
 * JD-Core Version:    0.5.4
 */