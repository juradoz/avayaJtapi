/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.callcenter.Agent;
/*    */ import javax.telephony.callcenter.events.ACDAddrBusyEv;
/*    */ 
/*    */ public final class TsapiAddrBusyEv extends TsapiACDAddrEv
/*    */   implements ACDAddrBusyEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 20 */     return 300;
/*    */   }
/*    */ 
/*    */   public TsapiAddrBusyEv(Address _device, Agent _agent, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 30 */     super(_device, _agent, _cause, _metaCode, _privateData);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrBusyEv
 * JD-Core Version:    0.5.4
 */