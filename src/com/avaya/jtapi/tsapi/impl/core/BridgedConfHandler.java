/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAClearConnectionConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class BridgedConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSConnection conn;
/*      */ 
/*      */   BridgedConfHandler(TSConnection _conn)
/*      */   {
/* 2260 */     this.conn = _conn;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 2265 */     if ((event == null) || (!(event.getEvent() instanceof CSTAClearConnectionConfEvent)))
/*      */     {
/* 2268 */       return;
/*      */     }
/*      */ 
/* 2271 */     this.conn.replyTermConnPriv = event.getPrivData();
/*      */ 
/* 2274 */     Vector eventList = new Vector();
/* 2275 */     if (this.conn.getTSConn().getTermConns().size() > 1)
/*      */     {
/* 2277 */       this.conn.setTermConnState(100, eventList);
/*      */     }
/*      */     else
/*      */     {
/* 2281 */       this.conn.setTermConnState(102, eventList);
/*      */     }
/* 2283 */     if (eventList.size() <= 0)
/*      */       return;
/* 2285 */     Vector observers = this.conn.getTSCall().getObservers();
/* 2286 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 2288 */       TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 2289 */       callback.deliverEvents(eventList, 100, false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.BridgedConfHandler
 * JD-Core Version:    0.5.4
 */