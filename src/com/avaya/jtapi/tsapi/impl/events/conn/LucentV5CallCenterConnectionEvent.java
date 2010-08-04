/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5CallInfo;
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.MetaEvent;
/*    */ 
/*    */ public class LucentV5CallCenterConnectionEvent extends LucentCallCenterConnectionEvent
/*    */   implements LucentV5CallInfo
/*    */ {
/*    */   public LucentV5CallCenterConnectionEvent(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 20 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public boolean canSetBillRate()
/*    */   {
/* 27 */     return this.callEventParams.isFlexibleBilling();
/*    */   }
/*    */ 
/*    */   public int getCallOriginatorType()
/*    */   {
/* 34 */     return this.callEventParams.getCallOriginatorType();
/*    */   }
/*    */ 
/*    */   public String getUCID()
/*    */   {
/* 41 */     return this.callEventParams.getUcid();
/*    */   }
/*    */ 
/*    */   public boolean hasCallOriginatorType()
/*    */   {
/* 48 */     return this.callEventParams.hasCallOriginatorType();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.LucentV5CallCenterConnectionEvent
 * JD-Core Version:    0.5.4
 */