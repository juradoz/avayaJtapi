/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.acs.ACSEventHeader;
/*     */ import com.avaya.jtapi.tsapi.asn1.TsapiPDU;
/*     */ 
/*     */ public final class CSTAEvent
/*     */ {
/*     */   ACSEventHeader eventHeader;
/*     */   TsapiPDU event;
/*     */   Object privData;
/*     */   long queuedTimeStamp;
/*     */ 
/*     */   CSTAEvent()
/*     */   {
/*  17 */     this.eventHeader = null;
/*  18 */     this.event = null;
/*  19 */     this.privData = null;
/*     */   }
/*     */ 
/*     */   CSTAEvent(ACSEventHeader _eventHeader, TsapiPDU _event)
/*     */   {
/*  24 */     this.eventHeader = _eventHeader;
/*  25 */     this.event = _event;
/*  26 */     this.privData = null;
/*     */   }
/*     */ 
/*     */   public CSTAEvent(ACSEventHeader _eventHeader, TsapiPDU _event, CSTAPrivate _priv)
/*     */   {
/*  31 */     this.eventHeader = _eventHeader;
/*  32 */     this.event = _event;
/*  33 */     this.privData = _priv;
/*     */   }
/*     */ 
/*     */   public TsapiPDU getEvent()
/*     */   {
/*  40 */     return this.event;
/*     */   }
/*     */ 
/*     */   public ACSEventHeader getEventHeader()
/*     */   {
/*  48 */     return this.eventHeader;
/*     */   }
/*     */ 
/*     */   public Object getPrivData()
/*     */   {
/*  56 */     return this.privData;
/*     */   }
/*     */ 
/*     */   public void setPrivData(Object privData)
/*     */   {
/*  63 */     this.privData = privData;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  69 */     return "CSTAEvent[" + getMyCustomString() + "]@" + Integer.toHexString(super.hashCode());
/*     */   }
/*     */ 
/*     */   private String getMyCustomString()
/*     */   {
/*     */     String s;
/*  77 */     if (this.event == null)
/*     */     {
/*  79 */       s = "-";
/*     */     }
/*     */     else
/*     */     {
/*  87 */       s = this.event.getClass().getName();
/*  88 */       int i = s.lastIndexOf('.');
/*     */ 
/*  90 */       s = (i >= 0) ? s.substring(i + 1) : s;
/*     */     }
/*  92 */     return s;
/*     */   }
/*     */ 
/*     */   public long getQueuedTimeStamp()
/*     */   {
/*  99 */     return this.queuedTimeStamp;
/*     */   }
/*     */ 
/*     */   public void setQueuedTimeStamp(long queuedTimeStamp)
/*     */   {
/* 106 */     this.queuedTimeStamp = queuedTimeStamp;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAEvent
 * JD-Core Version:    0.5.4
 */