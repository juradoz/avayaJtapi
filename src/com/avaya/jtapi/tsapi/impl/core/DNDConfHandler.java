/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryDndConfEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiTerminalMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class DNDConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */   int pdu;
/*      */   boolean enable;
/*      */ 
/*      */   DNDConfHandler(TSDevice _device, boolean _enable)
/*      */   {
/* 4274 */     this.device = _device;
/* 4275 */     this.pdu = 46;
/* 4276 */     this.enable = _enable;
/*      */   }
/*      */ 
/*      */   DNDConfHandler(TSDevice _device) {
/* 4280 */     this.device = _device;
/* 4281 */     this.pdu = 30;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4286 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 4290 */       return;
/*      */     }
/*      */ 
/* 4294 */     if (this.pdu == 30)
/*      */     {
/* 4296 */       this.enable = ((CSTAQueryDndConfEvent)event.getEvent()).isDoNotDisturb();
/*      */     }
/*      */ 
/* 4299 */     this.device.replyAddrPriv = event.getPrivData();
/* 4300 */     this.device.replyTermPriv = event.getPrivData();
/*      */ 
/* 4303 */     Vector eventList = new Vector();
/*      */ 
/* 4305 */     this.device.updateDNDState(this.enable, eventList);
/*      */ 
/* 4307 */     if (eventList.size() <= 0)
/*      */       return;
/* 4309 */     Vector observers = this.device.getAddressObservers();
/* 4310 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 4312 */       TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 4313 */       callback.deliverEvents(eventList, false);
/*      */     }
/* 4315 */     Vector terminalObservers = this.device.getTerminalObservers();
/* 4316 */     for (int j = 0; j < terminalObservers.size(); ++j)
/*      */     {
/* 4318 */       TsapiTerminalMonitor callback = (TsapiTerminalMonitor)terminalObservers.elementAt(j);
/* 4319 */       callback.deliverEvents(eventList, false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.DNDConfHandler
 * JD-Core Version:    0.5.4
 */