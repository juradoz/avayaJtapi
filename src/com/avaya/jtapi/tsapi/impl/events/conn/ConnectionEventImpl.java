/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventImpl;
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.Connection;
/*    */ import javax.telephony.ConnectionEvent;
/*    */ import javax.telephony.MetaEvent;
/*    */ 
/*    */ public class ConnectionEventImpl extends CallEventImpl
/*    */   implements ConnectionEvent
/*    */ {
/*    */   public ConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 22 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public Connection getConnection()
/*    */   {
/* 29 */     Connection connection = null;
/* 30 */     if (this.callEventParams instanceof ConnEventParams)
/* 31 */       connection = ((ConnEventParams)this.callEventParams).getConnection();
/* 32 */     return connection;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 40 */     if (this.callEventParams instanceof ConnEventParams) {
/* 41 */       return ((ConnEventParams)this.callEventParams).getConnection();
/*    */     }
/* 43 */     return super.getSource();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl
 * JD-Core Version:    0.5.4
 */