/*    */ package com.avaya.jtapi.tsapi.impl.events.termConn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;
/*    */ import javax.telephony.TerminalConnection;
/*    */ 
/*    */ public abstract class TsapiTermConnEvent extends TsapiCallEvent
/*    */ {
/*    */   public final TerminalConnection getTerminalConnection()
/*    */   {
/* 18 */     return ((TermConnEventParams)this.params).getTermConn();
/*    */   }
/*    */ 
/*    */   public TsapiTermConnEvent(TermConnEventParams params, int _eventPackage)
/*    */   {
/* 23 */     super(params, _eventPackage);
/*    */   }
/*    */ 
/*    */   public TsapiTermConnEvent(TermConnEventParams params)
/*    */   {
/* 28 */     this(params, 0);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnEvent
 * JD-Core Version:    0.5.4
 */