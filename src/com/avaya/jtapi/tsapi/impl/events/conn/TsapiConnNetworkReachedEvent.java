/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiCallInfo;
/*    */ import com.avaya.jtapi.tsapi.ITsapiConnNetworkReachedEvent;
/*    */ import com.avaya.jtapi.tsapi.NetworkProgressInfo;
/*    */ 
/*    */ public class TsapiConnNetworkReachedEvent extends TsapiCallCtlConnEvent
/*    */   implements ITsapiConnNetworkReachedEvent, ITsapiCallInfo
/*    */ {
/*    */   public final NetworkProgressInfo getNetworkProgressInfo()
/*    */   {
/* 18 */     if (this.privateData instanceof NetworkProgressInfo) {
/* 19 */       return (NetworkProgressInfo)this.privateData;
/*    */     }
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */   public final int getID()
/*    */   {
/* 27 */     return 210;
/*    */   }
/*    */ 
/*    */   public TsapiConnNetworkReachedEvent(ConnEventParams params)
/*    */   {
/* 35 */     super(params);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.TsapiConnNetworkReachedEvent
 * JD-Core Version:    0.5.4
 */