/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LookaheadInfo;
/*    */ import com.avaya.jtapi.tsapi.LucentCallInfo;
/*    */ import com.avaya.jtapi.tsapi.OriginalCallInfo;
/*    */ import com.avaya.jtapi.tsapi.UserEnteredCode;
/*    */ import com.avaya.jtapi.tsapi.UserToUserInfo;
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.callcenter.ACDAddress;
/*    */ import javax.telephony.callcenter.CallCenterAddress;
/*    */ import javax.telephony.callcenter.CallCenterTrunk;
/*    */ 
/*    */ public class LucentCallCenterConnectionEvent extends CallCenterConnectionEventImpl
/*    */   implements LucentCallInfo
/*    */ {
/*    */   public LucentCallCenterConnectionEvent(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 27 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public ACDAddress getDeliveringACDAddress()
/*    */   {
/* 34 */     return this.callEventParams.getSplit();
/*    */   }
/*    */ 
/*    */   public CallCenterAddress getDistributingAddress()
/*    */   {
/* 41 */     return this.callEventParams.getDistributingDevice();
/*    */   }
/*    */ 
/*    */   public LookaheadInfo getLookaheadInfo()
/*    */   {
/* 48 */     return this.callEventParams.getLookaheadInfo();
/*    */   }
/*    */ 
/*    */   public OriginalCallInfo getOriginalCallInfo()
/*    */   {
/* 55 */     return this.callEventParams.getOriginalCallInfo();
/*    */   }
/*    */ 
/*    */   public short getReason()
/*    */   {
/* 62 */     return this.callEventParams.getReason();
/*    */   }
/*    */ 
/*    */   public CallCenterTrunk getTrunk()
/*    */   {
/* 69 */     return this.callEventParams.getTrunk();
/*    */   }
/*    */ 
/*    */   public UserEnteredCode getUserEnteredCode()
/*    */   {
/* 76 */     return this.callEventParams.getUserEnteredCode();
/*    */   }
/*    */ 
/*    */   public UserToUserInfo getUserToUserInfo()
/*    */   {
/* 83 */     return this.callEventParams.getUserToUserInfo();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.LucentCallCenterConnectionEvent
 * JD-Core Version:    0.5.4
 */