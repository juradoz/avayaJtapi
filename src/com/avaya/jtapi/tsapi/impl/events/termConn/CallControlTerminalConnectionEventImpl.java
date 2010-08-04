/*    */ package com.avaya.jtapi.tsapi.impl.events.termConn;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
/*    */ 
/*    */ public class CallControlTerminalConnectionEventImpl extends TerminalConnectionEventImpl
/*    */   implements CallControlTerminalConnectionEvent
/*    */ {
/*    */   public CallControlTerminalConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 23 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public Address getCalledAddress()
/*    */   {
/* 30 */     return this.callEventParams.getCalledAddress();
/*    */   }
/*    */ 
/*    */   public Address getCallingAddress()
/*    */   {
/* 37 */     return this.callEventParams.getCallingAddress();
/*    */   }
/*    */ 
/*    */   public Terminal getCallingTerminal()
/*    */   {
/* 44 */     return this.callEventParams.getCallingTerminal();
/*    */   }
/*    */ 
/*    */   public Address getLastRedirectedAddress()
/*    */   {
/* 51 */     return this.callEventParams.getLastRedirectionAddress();
/*    */   }
/*    */ 
/*    */   public int getCallControlCause()
/*    */   {
/* 58 */     return this.callEventParams.getCause();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.CallControlTerminalConnectionEventImpl
 * JD-Core Version:    0.5.4
 */