/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import javax.telephony.events.ConnAlertingEv;
/*    */ 
/*    */ public final class TsapiConnAlertingEvent extends TsapiConnEvent
/*    */   implements ConnAlertingEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 16 */     return 104;
/*    */   }
/*    */ 
/*    */   public TsapiConnAlertingEvent(ConnEventParams params)
/*    */   {
/* 23 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnAlertingEvent
 * JD-Core Version:    0.5.4
 */