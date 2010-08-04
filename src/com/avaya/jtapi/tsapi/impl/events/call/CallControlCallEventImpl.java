/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcontrol.CallControlCallEvent;
/*    */ 
/*    */ public class CallControlCallEventImpl extends CallEventImpl
/*    */   implements CallControlCallEvent
/*    */ {
/*    */   public CallControlCallEventImpl(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 20 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public Address getCalledAddress()
/*    */   {
/* 27 */     return this.callEventParams.getCalledAddress();
/*    */   }
/*    */ 
/*    */   public Address getCallingAddress()
/*    */   {
/* 34 */     return this.callEventParams.getCallingAddress();
/*    */   }
/*    */ 
/*    */   public Terminal getCallingTerminal()
/*    */   {
/* 41 */     return this.callEventParams.getCallingTerminal();
/*    */   }
/*    */ 
/*    */   public Address getLastRedirectedAddress()
/*    */   {
/* 48 */     return this.callEventParams.getLastRedirectionAddress();
/*    */   }
/*    */ 
/*    */   public int getCallControlCause()
/*    */   {
/* 55 */     return this.callEventParams.getCause();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.CallControlCallEventImpl
 * JD-Core Version:    0.5.4
 */