/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import javax.telephony.callcenter.events.CallCentTrunkInvalidEv;
/*    */ 
/*    */ public final class TsapiTrunkInvalidEv extends TsapiCallCtrTrunkEvent
/*    */   implements CallCentTrunkInvalidEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 16 */     return 318;
/*    */   }
/*    */ 
/*    */   public TsapiTrunkInvalidEv(CallEventParams params)
/*    */   {
/* 24 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.TsapiTrunkInvalidEv
 * JD-Core Version:    0.5.4
 */