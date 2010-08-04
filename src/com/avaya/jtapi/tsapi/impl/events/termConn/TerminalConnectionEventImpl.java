/*    */ package com.avaya.jtapi.tsapi.impl.events.termConn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.TerminalConnection;
/*    */ import javax.telephony.TerminalConnectionEvent;
/*    */ 
/*    */ public class TerminalConnectionEventImpl extends ConnectionEventImpl
/*    */   implements TerminalConnectionEvent
/*    */ {
/*    */   public TerminalConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 23 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public TerminalConnection getTerminalConnection()
/*    */   {
/* 30 */     TerminalConnection tc = null;
/* 31 */     if (this.callEventParams instanceof TermConnEventParams)
/* 32 */       tc = ((TermConnEventParams)this.callEventParams).getTermConn();
/* 33 */     return tc;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 41 */     if (this.callEventParams instanceof TermConnEventParams) {
/* 42 */       return ((TermConnEventParams)this.callEventParams).getTermConn();
/*    */     }
/* 44 */     return super.getSource();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TerminalConnectionEventImpl
 * JD-Core Version:    0.5.4
 */