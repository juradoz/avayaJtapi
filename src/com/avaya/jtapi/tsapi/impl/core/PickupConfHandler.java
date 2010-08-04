/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAPickupCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class PickupConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */   TSDevice terminalAddress;
/*      */   TSConnection pickConnection;
/*      */ 
/*      */   PickupConfHandler(TSDevice _device, TSDevice _terminalAddress, TSConnection _pickConnection)
/*      */   {
/* 4450 */     this.device = _device;
/* 4451 */     this.terminalAddress = _terminalAddress;
/* 4452 */     this.pickConnection = _pickConnection;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4457 */     if ((event == null) || (!(event.getEvent() instanceof CSTAPickupCallConfEvent)))
/*      */     {
/* 4460 */       return;
/*      */     }
/*      */ 
/* 4463 */     this.device.replyTermPriv = event.getPrivData();
/*      */ 
/* 4466 */     Vector eventList = new Vector();
/* 4467 */     if (this.terminalAddress == this.pickConnection.getTSDevice())
/*      */     {
/* 4469 */       this.pickConnection.setConnectionState(88, eventList);
/*      */ 
/* 4471 */       if (eventList.size() <= 0)
/*      */         return;
/* 4473 */       TSCall pickCall = this.pickConnection.getTSCall();
/* 4474 */       Vector observers = pickCall.getObservers();
/* 4475 */       for (int j = 0; j < observers.size(); ++j)
/*      */       {
/* 4477 */         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 4478 */         callback.deliverEvents(eventList, 100, false);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 4484 */       this.pickConnection.setConnectionState(89, eventList);
/*      */ 
/* 4487 */       if (eventList.size() <= 0)
/*      */         return;
/* 4489 */       TSCall pickCall = this.pickConnection.getTSCall();
/* 4490 */       Vector observers = pickCall.getObservers();
/* 4491 */       for (int j = 0; j < observers.size(); ++j)
/*      */       {
/* 4493 */         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 4494 */         callback.deliverEvents(eventList, 100, false);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.PickupConfHandler
 * JD-Core Version:    0.5.4
 */