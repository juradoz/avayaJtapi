/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import javax.telephony.events.ConnConnectedEv;
/*    */ 
/*    */ public final class TsapiConnConnectedEvent extends TsapiConnEvent
/*    */   implements ConnConnectedEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 16 */     return 105;
/*    */   }
/*    */ 
/*    */   public TsapiConnConnectedEvent(ConnEventParams params)
/*    */   {
/* 23 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnConnectedEvent
 * JD-Core Version:    0.5.4
 */