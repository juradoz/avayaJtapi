/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.callcontrol.events.CallCtlAddrDoNotDisturbEv;
/*    */ 
/*    */ public final class TsapiAddressDNDEvent extends TsapiCallCtlAddressEvent
/*    */   implements CallCtlAddrDoNotDisturbEv
/*    */ {
/* 27 */   boolean state = false;
/*    */ 
/*    */   public boolean getDoNotDisturbState()
/*    */   {
/* 17 */     return this.state;
/*    */   }
/*    */ 
/*    */   public int getID()
/*    */   {
/* 23 */     return 200;
/*    */   }
/*    */ 
/*    */   public TsapiAddressDNDEvent(Address _device, boolean _state, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 34 */     super(_device, _cause, _metaCode, _privateData);
/* 35 */     this.state = _state;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressDNDEvent
 * JD-Core Version:    0.5.4
 */