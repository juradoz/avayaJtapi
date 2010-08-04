/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcenter.CallCenterConnectionEvent;
/*    */ 
/*    */ public class CallCenterConnectionEventImpl extends ConnectionEventImpl
/*    */   implements CallCenterConnectionEvent
/*    */ {
/*    */   public CallCenterConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 21 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public Object getApplicationData()
/*    */   {
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */   public Address getCalledAddress()
/*    */   {
/* 35 */     return this.callEventParams.getCalledAddress();
/*    */   }
/*    */ 
/*    */   public Address getCallingAddress()
/*    */   {
/* 42 */     return this.callEventParams.getCallingAddress();
/*    */   }
/*    */ 
/*    */   public Terminal getCallingTerminal()
/*    */   {
/* 49 */     return this.callEventParams.getCallingTerminal();
/*    */   }
/*    */ 
/*    */   public Address getLastRedirectedAddress()
/*    */   {
/* 56 */     return this.callEventParams.getLastRedirectionAddress();
/*    */   }
/*    */ 
/*    */   public int getCallCenterCause()
/*    */   {
/* 63 */     return this.callEventParams.getCause();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.CallCenterConnectionEventImpl
 * JD-Core Version:    0.5.4
 */