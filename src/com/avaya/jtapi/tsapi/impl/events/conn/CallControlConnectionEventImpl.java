/*    */ package com.avaya.jtapi.tsapi.impl.events.conn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcontrol.CallControlConnectionEvent;
/*    */ 
/*    */ public class CallControlConnectionEventImpl extends ConnectionEventImpl
/*    */   implements CallControlConnectionEvent
/*    */ {
/*    */   private String digits;
/*    */   private int numInQueue;
/*    */ 
/*    */   public CallControlConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId, int numInQueue, String digits)
/*    */   {
/* 25 */     super(params, event, eventId);
/* 26 */     this.numInQueue = numInQueue;
/* 27 */     this.digits = digits;
/*    */   }
/*    */ 
/*    */   public String getDigits()
/*    */   {
/* 34 */     return this.digits;
/*    */   }
/*    */ 
/*    */   public int getNumberInQueue()
/*    */   {
/* 41 */     return this.numInQueue;
/*    */   }
/*    */ 
/*    */   public Address getCalledAddress()
/*    */   {
/* 48 */     return this.callEventParams.getCalledAddress();
/*    */   }
/*    */ 
/*    */   public Address getCallingAddress()
/*    */   {
/* 55 */     return this.callEventParams.getCallingAddress();
/*    */   }
/*    */ 
/*    */   public Terminal getCallingTerminal()
/*    */   {
/* 62 */     return this.callEventParams.getCallingTerminal();
/*    */   }
/*    */ 
/*    */   public Address getLastRedirectedAddress()
/*    */   {
/* 69 */     return this.callEventParams.getLastRedirectionAddress();
/*    */   }
/*    */ 
/*    */   public int getCallControlCause()
/*    */   {
/* 76 */     return this.callEventParams.getCause();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.conn.CallControlConnectionEventImpl
 * JD-Core Version:    0.5.4
 */