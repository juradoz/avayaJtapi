/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAClearCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class ClearCallConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSCall call;
/*      */ 
/*      */   ClearCallConfHandler(TSCall _call)
/*      */   {
/* 5686 */     this.call = _call;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 5691 */     if ((event == null) || (!(event.getEvent() instanceof CSTAClearCallConfEvent)))
/*      */     {
/* 5694 */       return;
/*      */     }
/*      */ 
/* 5697 */     this.call.replyPriv = event.getPrivData();
/*      */ 
/* 5700 */     Vector eventList = new Vector();
/* 5701 */     this.call.setState(34, eventList);
/* 5702 */     if (eventList.size() > 0)
/*      */     {
/* 5704 */       Vector observers = this.call.getObservers();
/* 5705 */       for (int j = 0; j < observers.size(); ++j)
/*      */       {
/* 5707 */         TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 5708 */         callback.deliverEvents(eventList, 100, false);
/*      */       }
/*      */     }
/* 5711 */     this.call.endCVDObservers(100, null);
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.ClearCallConfHandler
 * JD-Core Version:    0.5.4
 */