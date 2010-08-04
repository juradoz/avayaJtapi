/*    */ package com.avaya.jtapi.tsapi.impl.events.addr;
/*    */ 
/*    */ import javax.telephony.Address;
/*    */ import javax.telephony.callcenter.Agent;
/*    */ import javax.telephony.callcenter.AgentTerminal;
/*    */ import javax.telephony.callcenter.CallCenterTrunk;
/*    */ 
/*    */ public abstract class TsapiACDAddrEv extends TsapiCallCentAddrEv
/*    */ {
/* 62 */   Agent agent = null;
/*    */ 
/*    */   public final Agent getAgent()
/*    */   {
/* 20 */     return this.agent;
/*    */   }
/*    */ 
/*    */   public final AgentTerminal getAgentTerminal()
/*    */   {
/* 26 */     return this.agent.getAgentTerminal();
/*    */   }
/*    */ 
/*    */   public final String getAgentID()
/*    */   {
/* 32 */     return this.agent.getAgentID();
/*    */   }
/*    */ 
/*    */   public final int getState()
/*    */   {
/* 38 */     return this.agent.getState();
/*    */   }
/*    */ 
/*    */   public final Address getAgentAddress()
/*    */   {
/* 44 */     return this.agent.getAgentAddress();
/*    */   }
/*    */ 
/*    */   public final CallCenterTrunk[] getTrunks()
/*    */   {
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   public TsapiACDAddrEv(Address _device, Agent _agent, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 58 */     super(_device, _cause, _metaCode, _privateData);
/* 59 */     this.agent = _agent;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiACDAddrEv
 * JD-Core Version:    0.5.4
 */