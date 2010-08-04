/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ 
/*      */ final class TermPrivConfHandler
/*      */   implements ConfHandler
/*      */ {
/*      */   TSDevice device;
/*      */   int pdu;
/*      */ 
/*      */   TermPrivConfHandler(TSDevice _device, int _pdu)
/*      */   {
/* 4508 */     this.device = _device;
/* 4509 */     this.pdu = _pdu;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/* 4514 */     if ((event == null) || (event.getEventHeader().getEventClass() != 5) || (event.getEventHeader().getEventType() != this.pdu))
/*      */     {
/* 4518 */       return;
/*      */     }
/*      */ 
/* 4521 */     this.device.replyTermPriv = event.getPrivData();
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TermPrivConfHandler
 * JD-Core Version:    0.5.4
 */