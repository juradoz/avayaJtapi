/*    */ package com.avaya.jtapi.tsapi.impl.events.terminal;
/*    */ 
/*    */ import javax.telephony.Terminal;
/*    */ import javax.telephony.callcenter.Agent;
/*    */ import javax.telephony.callcenter.events.AgentTermLoggedOffEv;
/*    */ 
/*    */ public final class TsapiTermLogOffEv extends TsapiAgentTermEv
/*    */   implements AgentTermLoggedOffEv
/*    */ {
/*    */   public int getID()
/*    */   {
/* 19 */     return 309;
/*    */   }
/*    */ 
/*    */   public TsapiTermLogOffEv(Terminal _device, Agent _agent, int _cause, int _metaCode, Object _privateData)
/*    */   {
/* 29 */     super(_device, _agent, _cause, _metaCode, _privateData);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TsapiTermLogOffEv
 * JD-Core Version:    0.5.4
 */