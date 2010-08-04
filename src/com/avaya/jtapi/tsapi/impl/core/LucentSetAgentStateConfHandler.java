/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentSetAgentStateConfEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ final class LucentSetAgentStateConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */ 
/*      */   LucentSetAgentStateConfHandler(TSDevice _device)
/*      */   {
/* 4535 */     this.device = _device;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4540 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != 50))
/*      */     {
/* 4544 */       return;
/*      */     }
/*      */ 
/* 4547 */     this.device.replyTermPriv = event.getPrivData();
/*      */ 
/* 4549 */     this.device.changesWereHeldPending = false;
/* 4550 */     if ((event.getPrivData() == null) || 
/* 4552 */       (!(event.getPrivData() instanceof LucentSetAgentStateConfEvent)))
/*      */       return;
/* 4554 */     this.device.changesWereHeldPending = ((LucentSetAgentStateConfEvent)event.getPrivData()).isPending();
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.LucentSetAgentStateConfHandler
 * JD-Core Version:    0.5.4
 */