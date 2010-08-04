/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class DisconnectedConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSConnection conn;
/* 2219 */   boolean handleIt = true;
/*      */   int pdu;
/*      */ 
/*      */   DisconnectedConfHandler(TSConnection _conn, int _pdu)
/*      */   {
/* 2224 */     this.conn = _conn;
/* 2225 */     this.pdu = _pdu;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 2230 */     if ((!this.handleIt) || (event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 2234 */       return;
/*      */     }
/*      */ 
/* 2237 */     this.conn.replyConnPriv = event.getPrivData();
/*      */ 
/* 2239 */     Vector eventList = new Vector();
/* 2240 */     this.conn.setConnectionState(89, eventList);
/*      */ 
/* 2243 */     if (eventList.size() <= 0)
/*      */       return;
/* 2245 */     Vector observers = this.conn.getTSCall().getObservers();
/* 2246 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 2248 */       TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 2249 */       callback.deliverEvents(eventList, 100, false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.DisconnectedConfHandler
 * JD-Core Version:    0.5.4
 */