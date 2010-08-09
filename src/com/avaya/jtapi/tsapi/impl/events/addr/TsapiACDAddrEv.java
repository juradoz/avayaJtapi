 package com.avaya.jtapi.tsapi.impl.events.addr;
 
 import javax.telephony.Address;
 import javax.telephony.callcenter.Agent;
 import javax.telephony.callcenter.AgentTerminal;
 import javax.telephony.callcenter.CallCenterTrunk;
 
 public abstract class TsapiACDAddrEv extends TsapiCallCentAddrEv
 {
   Agent agent = null;
 
   public final Agent getAgent()
   {
     return this.agent;
   }
 
   public final AgentTerminal getAgentTerminal()
   {
     return this.agent.getAgentTerminal();
   }
 
   public final String getAgentID()
   {
     return this.agent.getAgentID();
   }
 
   public final int getState()
   {
     return this.agent.getState();
   }
 
   public final Address getAgentAddress()
   {
     return this.agent.getAgentAddress();
   }
 
   public final CallCenterTrunk[] getTrunks()
   {
     return null;
   }
 
   public TsapiACDAddrEv(Address _device, Agent _agent, int _cause, int _metaCode, Object _privateData)
   {
     super(_device, _cause, _metaCode, _privateData);
     this.agent = _agent;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiACDAddrEv
 * JD-Core Version:    0.5.4
 */