/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import java.util.Vector;
/*      */ 
/*      */ final class TalkingConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSConnection conn;
/*      */   int pdu;
/*      */ 
/*      */   TalkingConfHandler(TSConnection _conn, int _pdu)
/*      */   {
/* 2185 */     this.conn = _conn;
/* 2186 */     this.pdu = _pdu;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 2191 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 2195 */       return;
/*      */     }
/*      */ 
/* 2198 */     this.conn.replyTermConnPriv = event.getPrivData();
/*      */ 
/* 2201 */     Vector eventList = new Vector();
/* 2202 */     this.conn.setConnectionState(88, eventList);
/* 2203 */     this.conn.setTermConnState(98, eventList);
/* 2204 */     if (eventList.size() <= 0)
/*      */       return;
/* 2206 */     Vector observers = this.conn.getTSCall().getObservers();
/* 2207 */     for (int j = 0; j < observers.size(); ++j)
/*      */     {
/* 2209 */       TsapiCallMonitor callback = (TsapiCallMonitor)observers.elementAt(j);
/* 2210 */       callback.deliverEvents(eventList, 100, false);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TalkingConfHandler
 * JD-Core Version:    0.5.4
 */