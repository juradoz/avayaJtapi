/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentCallControlAddressMsgWaitingEvent;
/*    */ import javax.telephony.Address;
/*    */ 
/*    */ public class LucentCallControlAddressMsgWaitingEventImpl extends CallControlAddressEventImpl
/*    */   implements LucentCallControlAddressMsgWaitingEvent
/*    */ {
/*    */   public LucentCallControlAddressMsgWaitingEventImpl(AddressEventParams addressEventParams, Address address)
/*    */   {
/* 13 */     super(addressEventParams, address);
/*    */   }
/*    */ 
/*    */   public int getMessageWaitingBits() {
/* 17 */     return this.mwBits;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.LucentCallControlAddressMsgWaitingEventImpl
 * JD-Core Version:    0.5.4
 */