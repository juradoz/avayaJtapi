/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import javax.telephony.Call;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.MultiCallMetaEvent;
/*    */ 
/*    */ public class MultiCallMetaEventImpl
/*    */   implements MultiCallMetaEvent
/*    */ {
/*    */   private CallEventParams callEventParams;
/*    */   private int id;
/*    */ 
/*    */   public MultiCallMetaEventImpl(CallEventParams params, int id)
/*    */   {
/* 20 */     this.callEventParams = params;
/* 21 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public Call getNewCall()
/*    */   {
/* 28 */     return this.callEventParams.getCall();
/*    */   }
/*    */ 
/*    */   public Call[] getOldCalls()
/*    */   {
/* 35 */     if (this.callEventParams.getOldCalls() == null)
/* 36 */       return null;
/* 37 */     Call[] oldCallArray = new Call[this.callEventParams.getOldCalls().size()];
/* 38 */     return (Call[])this.callEventParams.getOldCalls().toArray(oldCallArray);
/*    */   }
/*    */ 
/*    */   public int getCause()
/*    */   {
/* 45 */     return this.callEventParams.getCause();
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 52 */     return this.id;
/*    */   }
/*    */ 
/*    */   public MetaEvent getMetaEvent()
/*    */   {
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 67 */     return this.callEventParams.getCall();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.MultiCallMetaEventImpl
 * JD-Core Version:    0.5.4
 */