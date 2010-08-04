/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryMwiConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryMwiConfEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiAddressMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class MWIConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */   int pdu;
/*      */   int bits;
/*      */ 
/*      */   MWIConfHandler(TSDevice _device, int _bits)
/*      */   {
/* 4332 */     this.device = _device;
/* 4333 */     this.pdu = 44;
/* 4334 */     this.bits = _bits;
/*      */   }
/*      */ 
/*      */   MWIConfHandler(TSDevice _device) {
/* 4338 */     this.device = _device;
/* 4339 */     this.pdu = 28;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4344 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 4348 */       return;
/*      */     }
/*      */ 
/* 4352 */     if (this.pdu == 28)
/*      */     {
/* 4355 */       boolean enable = ((CSTAQueryMwiConfEvent)event.getEvent()).isMessages();
/* 4356 */       if (event.getPrivData() instanceof LucentQueryMwiConfEvent)
/*      */       {
/* 4358 */         LucentQueryMwiConfEvent luPrivData = (LucentQueryMwiConfEvent)event.getPrivData();
/* 4359 */         this.bits = luPrivData.getApplicationType();
/*      */       }
/* 4361 */       else if (enable)
/*      */       {
/* 4364 */         this.bits = -1;
/*      */       }
/*      */       else
/*      */       {
/* 4368 */         this.bits = 0;
/*      */       }
/*      */     }
/*      */ 
/* 4372 */     this.device.replyAddrPriv = event.getPrivData();
/*      */ 
/* 4375 */     Vector eventList = new Vector();
/*      */ 
/* 4377 */     this.device.updateMessageWaitingBits(this.bits, eventList);
/*      */ 
/* 4379 */     if (eventList.size() <= 0)
/*      */       return;
/* 4381 */     Vector observers = this.device.getAddressObservers();
/* 4382 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 4384 */       TsapiAddressMonitor callback = (TsapiAddressMonitor)observers.elementAt(j);
/* 4385 */       callback.deliverEvents(eventList, false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.MWIConfHandler
 * JD-Core Version:    0.5.4
 */