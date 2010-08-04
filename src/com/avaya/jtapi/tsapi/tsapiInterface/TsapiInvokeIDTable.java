/*     */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TsapiInvokeIDTable
/*     */ {
/*  27 */   private static Logger log = Logger.getLogger(TsapiInvokeIDTable.class);
/*     */   private Hashtable<Integer, TSInvokeID> invokeIDTbl;
/*  30 */   private int nextInvokeID = 1;
/*     */   private String debugID;
/*     */ 
/*     */   public TsapiInvokeIDTable(String _debugID)
/*     */   {
/*  42 */     this.debugID = _debugID;
/*  43 */     this.invokeIDTbl = new Hashtable();
/*     */   }
/*     */ 
/*     */   public TSInvokeID allocTSInvokeID(ConfHandler handler)
/*     */   {
/*  59 */     synchronized (this.invokeIDTbl)
/*     */     {
/*  61 */       TSInvokeID invokeID = new TSInvokeID(this.nextInvokeID, handler, this.debugID);
/*  62 */       Object oldObj = this.invokeIDTbl.put(new Integer(this.nextInvokeID), invokeID);
/*  63 */       if (oldObj != null) {
/*  64 */         log.info("NOTICE: invokeIDTbl.put() replaced " + oldObj + " for " + this.debugID);
/*     */       }
/*  66 */       this.nextInvokeID += 1;
/*     */ 
/*  68 */       return invokeID;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deallocTSInvokeID(TSInvokeID invokeID)
/*     */   {
/*  82 */     if (this.invokeIDTbl.remove(new Integer(invokeID.getValue())) != null)
/*     */       return;
/*  84 */     log.info("couldn't dealloc invokeID " + invokeID.getValue() + " for " + this.debugID);
/*     */   }
/*     */ 
/*     */   public TSInvokeID getTSInvokeID(int value)
/*     */   {
/*  99 */     TSInvokeID invokeID = (TSInvokeID)this.invokeIDTbl.get(new Integer(value));
/* 100 */     if ((invokeID == null) || (invokeID.getValue() != value))
/*     */     {
/* 102 */       log.info("couldn't find invokeID " + value + " for " + this.debugID);
/*     */     }
/* 104 */     return invokeID;
/*     */   }
/*     */ 
/*     */   public void requestTimeOut(ConfHandler handler)
/*     */   {
/* 115 */     Iterator ids = this.invokeIDTbl.values().iterator();
/* 116 */     while (ids.hasNext()) {
/* 117 */       TSInvokeID id = (TSInvokeID)ids.next();
/* 118 */       if (id.getConfHandler().equals(handler)) {
/* 119 */         deallocTSInvokeID(id);
/* 120 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 132 */     Iterator ids = this.invokeIDTbl.values().iterator();
/* 133 */     while (ids.hasNext()) {
/* 134 */       TSInvokeID id = (TSInvokeID)ids.next();
/* 135 */       id.setConf(null);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.TsapiInvokeIDTable
 * JD-Core Version:    0.5.4
 */