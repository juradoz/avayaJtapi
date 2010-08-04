/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import javax.telephony.events.ConnDisconnectedEv;
/*    */ 
/*    */ public final class TsapiConnDisconnectedEvent extends TsapiConnEvent
/*    */   implements ConnDisconnectedEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 16 */     return 107;
/*    */   }
/*    */ 
/*    */   public TsapiConnDisconnectedEvent(ConnEventParams params)
/*    */   {
/* 24 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnDisconnectedEvent
 * JD-Core Version:    0.5.4
 */