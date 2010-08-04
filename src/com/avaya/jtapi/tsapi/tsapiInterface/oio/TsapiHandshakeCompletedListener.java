/*    */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*    */ 
/*    */ import javax.net.ssl.HandshakeCompletedEvent;
/*    */ import javax.net.ssl.HandshakeCompletedListener;
/*    */ import javax.net.ssl.SSLSession;
/*    */ 
/*    */ class TsapiHandshakeCompletedListener
/*    */   implements HandshakeCompletedListener
/*    */ {
/*    */   private HandshakeCompletedEvent event;
/*    */ 
/*    */   TsapiHandshakeCompletedListener()
/*    */   {
/* 10 */     this.event = null;
/*    */   }
/*    */ 
/*    */   public void handshakeCompleted(HandshakeCompletedEvent event) {
/* 14 */     this.event = event;
/*    */ 
/* 18 */     synchronized (this)
/*    */     {
/* 20 */       super.notify();
/*    */     }
/*    */   }
/*    */ 
/*    */   SSLSession getSslSession()
/*    */   {
/* 29 */     if (this.event != null)
/*    */     {
/* 31 */       return this.event.getSession();
/*    */     }
/*    */ 
/* 34 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.TsapiHandshakeCompletedListener
 * JD-Core Version:    0.5.4
 */