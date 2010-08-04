/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import javax.telephony.events.ConnCreatedEv;
/*    */ 
/*    */ public final class TsapiConnCreatedEvent extends TsapiConnEvent
/*    */   implements ConnCreatedEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 16 */     return 106;
/*    */   }
/*    */ 
/*    */   public TsapiConnCreatedEvent(ConnEventParams params)
/*    */   {
/* 23 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnCreatedEvent
 * JD-Core Version:    0.5.4
 */