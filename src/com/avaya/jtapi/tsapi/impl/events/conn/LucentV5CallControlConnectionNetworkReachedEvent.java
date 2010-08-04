/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5Call;
/*    */ import com.avaya.jtapi.tsapi.LucentV5CallInfo;
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.MetaEvent;
/*    */ 
/*    */ public class LucentV5CallControlConnectionNetworkReachedEvent extends LucentCallControlConnectionNetworkReachedEvent
/*    */   implements LucentV5CallInfo
/*    */ {
/*    */   public LucentV5CallControlConnectionNetworkReachedEvent(CallEventParams params, MetaEvent event, int eventId, int numInQueue, String digits)
/*    */   {
/* 30 */     super(params, event, eventId, numInQueue, digits);
/*    */   }
/*    */ 
/*    */   public boolean canSetBillRate()
/*    */   {
/* 37 */     return this.callEventParams.isFlexibleBilling();
/*    */   }
/*    */ 
/*    */   public int getCallOriginatorType()
/*    */   {
/* 44 */     return this.callEventParams.getCallOriginatorType();
/*    */   }
/*    */ 
/*    */   public String getUCID()
/*    */   {
/* 51 */     String ucid = null;
/* 52 */     if (this.callEventParams.getUcid() != null) {
/* 53 */       ucid = this.callEventParams.getUcid();
/*    */     }
/* 55 */     else if (getCall() instanceof LucentV5Call) {
/* 56 */       ucid = ((LucentV5Call)getCall()).getUCID();
/*    */     }
/* 58 */     return ucid;
/*    */   }
/*    */ 
/*    */   public boolean hasCallOriginatorType()
/*    */   {
/* 65 */     return this.callEventParams.hasCallOriginatorType();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallControlConnectionNetworkReachedEvent
 * JD-Core Version:    0.5.4
 */