 package com.avaya.jtapi.tsapi.impl.events.terminal;
 
 import javax.telephony.Address;
 import javax.telephony.Terminal;
 import javax.telephony.callcenter.ACDAddress;
 import javax.telephony.callcenter.Agent;
 
 public abstract class TsapiAgentTermEv extends TsapiCallCentTermEv
 {
   public Agent agent = null;
 
   public final Agent getAgent()
   {
     return this.agent;
   }
 
   public final ACDAddress getACDAddress()
   {
     return this.agent.getACDAddress();
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
 
   public TsapiAgentTermEv(Terminal _device, Agent _agent, int _cause, int _metaCode, Object _privateData)
   {
     super(_device, _cause, _metaCode, _privateData);
     this.agent = _agent;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.TsapiAgentTermEv
 * JD-Core Version:    0.5.4
 */