/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallCenterEvent;
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.callcenter.ACDAddressEvent;
/*    */ import javax.telephony.callcenter.Agent;
/*    */ 
/*    */ public class ACDAddressEventImpl extends TsapiListenerCallCenterEvent
/*    */   implements ACDAddressEvent
/*    */ {
/*    */   Agent agent;
/*    */   Address address;
/*    */ 
/*    */   public ACDAddressEventImpl(AddressEventParams addressEventParams, Address address, Agent agent, Object privateData)
/*    */   {
/* 18 */     super(addressEventParams.getEventId(), addressEventParams.getCause(), addressEventParams.getMetaEvent(), addressEventParams.getSource(), privateData);
/*    */ 
/* 21 */     this.agent = agent;
/* 22 */     this.address = address;
/*    */   }
/*    */ 
/*    */   public Agent getAgent()
/*    */   {
/* 27 */     return this.agent;
/*    */   }
/*    */ 
/*    */   public Address getAddress()
/*    */   {
/* 32 */     return this.address;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.ACDAddressEventImpl
 * JD-Core Version:    0.5.4
 */