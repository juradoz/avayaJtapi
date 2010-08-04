/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.acs.ACSConfirmation;
/*     */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAConfirmation;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTAPrivate;
/*     */ import com.avaya.jtapi.tsapi.csta1.CSTATSProvider;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TsapiEventDistributor
/*     */   implements TsapiEventHandler
/*     */ {
/*  35 */   private static Logger log = Logger.getLogger(TsapiEventDistributor.class);
/*     */   private TsapiUnsolicitedHandler handler;
/*     */   private TsapiInvokeIDTable invokeTable;
/*     */   private String debugID;
/*     */ 
/*     */   public TsapiEventDistributor(TsapiInvokeIDTable _invokeTable, String _debugID)
/*     */   {
/*  55 */     this.invokeTable = _invokeTable;
/*     */   }
/*     */ 
/*     */   public void handleEvent(CSTAEvent event)
/*     */   {
/*     */     try
/*     */     {
/*  72 */       long begin = System.currentTimeMillis();
/*     */ 
/*  76 */       CSTAPrivate.translatePrivateData(event, this.debugID);
/*     */ 
/*  78 */       switch (event.getEventHeader().getEventClass())
/*     */       {
/*     */       case 2:
/*  84 */         ACSConfirmation acsConf = (ACSConfirmation)event.getEvent();
/*  85 */         TSInvokeID invokeID = this.invokeTable.getTSInvokeID(acsConf.getInvokeID());
/*  86 */         if (invokeID != null)
/*     */         {
/*  88 */           this.invokeTable.deallocTSInvokeID(invokeID);
/*  89 */           invokeID.setConf(event); } break;
/*     */       case 5:
/*  98 */         CSTAConfirmation cstaConf = (CSTAConfirmation)event.getEvent();
/*  99 */          invokeID = this.invokeTable.getTSInvokeID(cstaConf.getInvokeID());
/* 100 */         if (invokeID != null)
/*     */         {
/* 102 */           this.invokeTable.deallocTSInvokeID(invokeID);
/* 103 */           invokeID.setConf(event); } break;
/*     */       case 1:
/* 109 */         this.handler.acsUnsolicited(event);
/* 110 */         break;
/*     */       case 4:
/* 113 */         this.handler.cstaUnsolicited(event);
/* 114 */         break;
/*     */       case 3:
/* 117 */         this.handler.cstaRequest(event);
/* 118 */         break;
/*     */       case 6:
/* 121 */         this.handler.cstaEventReport(event);
/* 122 */         break;
/*     */       default:
/* 125 */         log.info("DISTRIBUTE thread: WARNING: bad event in TSDistributeCstaEventThread");
/*     */       }
/*     */ 
/* 130 */       long end = System.currentTimeMillis();
/* 131 */       long delay = end - begin;
/*     */ 
/* 137 */       long threshold = CSTATSProvider.getHandleCSTAEventTimeThreshold();
/* 138 */       if ((threshold > 0L) && (delay > threshold))
/*     */       {
/* 141 */         log.info("TsapiEventDistributor.handleEvent(): exceeded threshold (" + threshold + ") for event " + event + ": " + delay);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 148 */       if (this.handler == null) {
/* 149 */         log.error("TsapiSession: no handler when Exception received." + e);
/*     */ 
/* 151 */         log.error(e.getMessage(), e);
/* 152 */         return;
/*     */       }
/* 154 */       this.handler.eventDistributorException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void setUnsolicitedHandler(TsapiUnsolicitedHandler handler)
/*     */   {
/* 164 */     this.handler = handler;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiEventDistributor
 * JD-Core Version:    0.5.4
 */