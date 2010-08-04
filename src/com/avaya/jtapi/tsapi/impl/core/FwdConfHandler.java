/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAForwardingInfo;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryFwdConfEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class FwdConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */   int pdu;
/*      */   CSTAForwardingInfo[] fwdInfo;
/*      */ 
/*      */   FwdConfHandler(TSDevice _device, CSTAForwardingInfo[] _fwdInfo)
/*      */   {
/* 4398 */     this.device = _device;
/* 4399 */     this.pdu = 48;
/* 4400 */     this.fwdInfo = _fwdInfo;
/*      */   }
/*      */ 
/*      */   FwdConfHandler(TSDevice _device)
/*      */   {
/* 4405 */     this.device = _device;
/* 4406 */     this.pdu = 32;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4411 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 4415 */       return;
/*      */     }
/*      */ 
/* 4419 */     if (this.pdu == 32)
/*      */     {
/* 4422 */       this.fwdInfo = ((CSTAQueryFwdConfEvent)event.getEvent()).getForward();
/*      */     }
/*      */ 
/* 4425 */     this.device.replyAddrPriv = event.getPrivData();
/*      */ 
/* 4428 */     Vector eventList = new Vector();
/* 4429 */     this.device.updateForwarding(this.fwdInfo, eventList);
/*      */ 
/* 4431 */     if (eventList.size() <= 0)
/*      */       return;
/* 4433 */     Vector observers = this.device.getAddressObservers();
/* 4434 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 4436 */       TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 4437 */       callback.deliverEvents(eventList, false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.FwdConfHandler
 * JD-Core Version:    0.5.4
 */