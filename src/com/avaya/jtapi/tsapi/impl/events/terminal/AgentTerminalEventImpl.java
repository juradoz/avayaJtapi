 package com.avaya.jtapi.tsapi.impl.events.terminal;
 
 import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallCenterEvent;
 import javax.telephony.Terminal;
 import javax.telephony.callcenter.Agent;
 import javax.telephony.callcenter.AgentTerminalEvent;
 
 public class AgentTerminalEventImpl extends TsapiListenerCallCenterEvent
   implements AgentTerminalEvent
 {
   private Terminal terminal;
   private Agent agent;
 
   public AgentTerminalEventImpl(TerminalEventParams terminalEventParams, Agent agent)
   {
     super(terminalEventParams.getEventId(), terminalEventParams.getCause(), terminalEventParams.getMetaEvent(), terminalEventParams.getSource(), terminalEventParams.getPrivateData());
 
     this.terminal = terminalEventParams.getTerminal();
     this.agent = agent;
   }
 
   public Terminal getTerminal()
   {
     return this.terminal;
   }
 
   public Agent getAgent()
   {
     return this.agent;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.terminal.AgentTerminalEventImpl
 * JD-Core Version:    0.5.4
 */