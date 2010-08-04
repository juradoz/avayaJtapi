/*    */ package com.avaya.jtapi.tsapi.impl.events.termConn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.TerminalConnection;
/*    */ 
/*    */ public class TermConnEventParams extends CallEventParams
/*    */ {
/*    */   private TerminalConnection termConn;
/*    */ 
/*    */   public TermConnEventParams()
/*    */   {
/*  8 */     this.termConn = null;
/*    */   }
/*    */   public TerminalConnection getTermConn() {
/* 11 */     return this.termConn;
/*    */   }
/*    */   public void setTermConn(TerminalConnection termConn) {
/* 14 */     this.termConn = termConn;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TermConnEventParams
 * JD-Core Version:    0.5.4
 */