/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallControlEvent;
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.callcontrol.CallControlAddressEvent;
/*    */ import javax.telephony.callcontrol.CallControlForwarding;
/*    */ 
/*    */ public class CallControlAddressEventImpl extends TsapiListenerCallControlEvent
/*    */   implements CallControlAddressEvent
/*    */ {
/*    */   CallControlForwarding[] callControlForwarding;
/*    */   boolean doNotDisturbState;
/* 14 */   int mwBits = 0;
/*    */   Address address;
/*    */ 
/*    */   public CallControlAddressEventImpl(AddressEventParams addressEventParams, Address address)
/*    */   {
/* 20 */     super(addressEventParams.getEventId(), addressEventParams.getCause(), addressEventParams.getMetaEvent(), addressEventParams.getSource(), addressEventParams.getPrivateData());
/*    */ 
/* 23 */     this.callControlForwarding = addressEventParams.getCallControlForwarding();
/* 24 */     this.mwBits = addressEventParams.getMwBits();
/* 25 */     this.doNotDisturbState = addressEventParams.isDoNotDisturbState();
/* 26 */     this.address = address;
/*    */   }
/*    */ 
/*    */   public boolean getDoNotDisturbState()
/*    */   {
/* 31 */     return this.doNotDisturbState;
/*    */   }
/*    */ 
/*    */   public CallControlForwarding[] getForwarding()
/*    */   {
/* 36 */     return this.callControlForwarding;
/*    */   }
/*    */ 
/*    */   public boolean getMessageWaitingState()
/*    */   {
/* 41 */     return this.mwBits != 0;
/*    */   }
/*    */ 
/*    */   public Address getAddress()
/*    */   {
/* 46 */     return this.address;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.CallControlAddressEventImpl
 * JD-Core Version:    0.5.4
 */