/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.callcontrol.CallControlForwarding;
/*    */ import javax.telephony.callcontrol.events.CallCtlAddrForwardEv;
/*    */ 
/*    */ public final class TsapiAddressForwardEvent extends TsapiCallCtlAddressEvent
/*    */   implements CallCtlAddrForwardEv
/*    */ {
/* 16 */   CallControlForwarding[] forwarding = null;
/*    */ 
/*    */   public CallControlForwarding[] getForwarding()
/*    */   {
/* 20 */     return this.forwarding;
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 26 */     return 201;
/*    */   }
/*    */ 
/*    */   public TsapiAddressForwardEvent(Address _device, CallControlForwarding[] _forwarding, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 33 */     super(_device, _cause, _metaCode, _privateData);
/* 34 */     this.forwarding = _forwarding;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressForwardEvent
 * JD-Core Version:    0.5.4
 */