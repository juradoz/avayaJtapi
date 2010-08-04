/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAHoldCallConfEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class HoldConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSConnection conn;
/*      */ 
/*      */   HoldConfHandler(TSConnection _conn)
/*      */   {
/* 2300 */     this.conn = _conn;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 2305 */     if ((event == null) || (!(event.getEvent() instanceof CSTAHoldCallConfEvent)))
/*      */     {
/* 2308 */       return;
/*      */     }
/*      */ 
/* 2311 */     this.conn.replyTermConnPriv = event.getPrivData();
/*      */ 
/* 2314 */     Vector eventList = new Vector();
/* 2315 */     this.conn.setConnectionState(88, eventList);
/* 2316 */     this.conn.setTermConnState(99, eventList);
/* 2317 */     if (eventList.size() <= 0)
/*      */       return;
/* 2319 */     Vector observers = this.conn.getTSCall().getObservers();
/* 2320 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 2322 */       TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 2323 */       callback.deliverEvents(eventList, 100, false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.HoldConfHandler
 * JD-Core Version:    0.5.4
 */