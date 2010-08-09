 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentSetAgentStateConfEvent;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 
 final class LucentSetAgentStateConfHandler
   implements ConfHandler
 {
   TSDevice device;
 
   LucentSetAgentStateConfHandler(TSDevice _device)
   {
     this.device = _device;
   }
 
   public void handleConf(CSTAEvent event)
   {
     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != 50))
     {
       return;
     }
 
     this.device.replyTermPriv = event.getPrivData();
 
     this.device.changesWereHeldPending = false;
     if ((event.getPrivData() == null) || 
       (!(event.getPrivData() instanceof LucentSetAgentStateConfEvent)))
       return;
     this.device.changesWereHeldPending = ((LucentSetAgentStateConfEvent)event.getPrivData()).isPending();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.LucentSetAgentStateConfHandler
 * JD-Core Version:    0.5.4
 */