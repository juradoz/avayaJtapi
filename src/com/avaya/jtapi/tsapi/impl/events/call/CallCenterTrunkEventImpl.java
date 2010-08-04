/*    */ package com.avaya.jtapi.tsapi.impl.events.call;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.MetaEvent;
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcenter.CallCenterTrunk;
/*    */ import javax.telephony.callcenter.CallCenterTrunkEvent;
/*    */ 
/*    */ public class CallCenterTrunkEventImpl extends CallEventImpl
/*    */   implements CallCenterTrunkEvent
/*    */ {
/*    */   public CallCenterTrunkEventImpl(CallEventParams params, MetaEvent event, int eventId)
/*    */   {
/* 21 */     super(params, event, eventId);
/*    */   }
/*    */ 
/*    */   public CallCenterTrunk getTrunk()
/*    */   {
/* 28 */     return this.callEventParams.getTrunk();
/*    */   }
/*    */ 
/*    */   public Object getApplicationData()
/*    */   {
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   public Address getCalledAddress()
/*    */   {
/* 42 */     return this.callEventParams.getCalledAddress();
/*    */   }
/*    */ 
/*    */   public Address getCallingAddress()
/*    */   {
/* 49 */     return this.callEventParams.getCallingAddress();
/*    */   }
/*    */ 
/*    */   public Terminal getCallingTerminal()
/*    */   {
/* 56 */     return this.callEventParams.getCallingTerminal();
/*    */   }
/*    */ 
/*    */   public Address getLastRedirectedAddress()
/*    */   {
/* 63 */     return this.callEventParams.getLastRedirectionAddress();
/*    */   }
/*    */ 
/*    */   public int getCallCenterCause()
/*    */   {
/* 70 */     return this.callEventParams.getCause();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.call.CallCenterTrunkEventImpl
 * JD-Core Version:    0.5.4
 */