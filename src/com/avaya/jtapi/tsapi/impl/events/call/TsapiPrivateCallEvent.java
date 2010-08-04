/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import javax.telephony.privatedata.events.PrivateCallEv;
/*    */ 
/*    */ public final class TsapiPrivateCallEvent extends TsapiCallEvent
/*    */   implements PrivateCallEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 17 */     return 601;
/*    */   }
/*    */ 
/*    */   public TsapiPrivateCallEvent(CallEventParams params)
/*    */   {
/* 24 */     super(params, 5);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.TsapiPrivateCallEvent
 * JD-Core Version:    0.5.4
 */