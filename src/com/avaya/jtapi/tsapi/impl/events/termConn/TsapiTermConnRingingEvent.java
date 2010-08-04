/*    */ package com.avaya.jtapi.tsapi.impl.events.termConn;
/*    */ 
/*    */ import javax.telephony.events.TermConnRingingEv;
/*    */ 
/*    */ public final class TsapiTermConnRingingEvent extends TsapiTermConnEvent
/*    */   implements TermConnRingingEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 16 */     return 119;
/*    */   }
/*    */ 
/*    */   public TsapiTermConnRingingEvent(TermConnEventParams params)
/*    */   {
/* 24 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnRingingEvent
 * JD-Core Version:    0.5.4
 */