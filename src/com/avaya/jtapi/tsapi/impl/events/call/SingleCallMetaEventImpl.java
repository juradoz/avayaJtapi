/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import javax.telephony.Call;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.SingleCallMetaEvent;
/*    */ 
/*    */ public class SingleCallMetaEventImpl
/*    */   implements SingleCallMetaEvent
/*    */ {
/*    */   private CallEventParams callEventParams;
/*    */   private int id;
/*    */ 
/*    */   public SingleCallMetaEventImpl(CallEventParams params, int id)
/*    */   {
/* 20 */     this.callEventParams = params;
/* 21 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public int getCause()
/*    */   {
/* 28 */     return this.callEventParams.getCause();
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 35 */     return this.id;
/*    */   }
/*    */ 
/*    */   public MetaEvent getMetaEvent()
/*    */   {
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 50 */     return this.callEventParams.getCall();
/*    */   }
/*    */ 
/*    */   public Call getCall() {
/* 54 */     return this.callEventParams.getCall();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.SingleCallMetaEventImpl
 * JD-Core Version:    0.5.4
 */