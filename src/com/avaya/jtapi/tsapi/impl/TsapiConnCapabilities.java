/*     */ package com.avaya.jtapi.tsapi.impl;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
/*     */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*     */ import javax.telephony.callcenter.capabilities.ACDConnectionCapabilities;
/*     */ import javax.telephony.callcenter.capabilities.ACDManagerConnectionCapabilities;
/*     */ import javax.telephony.callcontrol.capabilities.CallControlConnectionCapabilities;
/*     */ import javax.telephony.capabilities.ConnectionCapabilities;
/*     */ 
/*     */ public final class TsapiConnCapabilities
/*     */   implements ConnectionCapabilities, CallControlConnectionCapabilities, ACDConnectionCapabilities, ACDManagerConnectionCapabilities
/*     */ {
/*  19 */   private TSCapabilities tsCaps = null;
/*     */ 
/*     */   public boolean canDisconnect()
/*     */   {
/*  25 */     TsapiTrace.traceEntry("canDisconnect[]", this);
/*  26 */     boolean can = this.tsCaps.getClearConnection() == 1;
/*  27 */     TsapiTrace.traceExit("canDisconnect[]", this);
/*  28 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canRedirect()
/*     */   {
/*  35 */     TsapiTrace.traceEntry("canRedirect[]", this);
/*  36 */     boolean can = this.tsCaps.getDeflectCall() == 1;
/*  37 */     TsapiTrace.traceExit("canRedirect[]", this);
/*  38 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canAddToAddress()
/*     */   {
/*  43 */     TsapiTrace.traceEntry("canAddToAddress[]", this);
/*  44 */     TsapiTrace.traceExit("canAddToAddress[]", this);
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canAccept()
/*     */   {
/*  50 */     TsapiTrace.traceEntry("canAccept[]", this);
/*  51 */     TsapiTrace.traceExit("canAccept[]", this);
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canReject()
/*     */   {
/*  57 */     TsapiTrace.traceEntry("canReject[]", this);
/*  58 */     TsapiTrace.traceExit("canReject[]", this);
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canPark()
/*     */   {
/*  64 */     TsapiTrace.traceEntry("canPark[]", this);
/*  65 */     TsapiTrace.traceExit("canPark[]", this);
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean canGetACDManagerConnection()
/*     */   {
/*  73 */     TsapiTrace.traceEntry("canGetACDManagerConnection[]", this);
/*  74 */     boolean can = this.tsCaps.isLucent();
/*  75 */     TsapiTrace.traceExit("canGetACDManagerConnection[]", this);
/*  76 */     return can;
/*     */   }
/*     */ 
/*     */   public boolean canGetACDConnections()
/*     */   {
/*  83 */     TsapiTrace.traceEntry("canGetACDConnections[]", this);
/*  84 */     boolean can = this.tsCaps.isLucent();
/*  85 */     TsapiTrace.traceExit("canGetACDConnections[]", this);
/*  86 */     return can;
/*     */   }
/*     */ 
/*     */   public TsapiConnCapabilities(TSCapabilities _tsCaps)
/*     */   {
/*  92 */     this.tsCaps = _tsCaps;
/*  93 */     TsapiTrace.traceConstruction(this, TsapiConnCapabilities.class);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  99 */     super.finalize();
/* 100 */     TsapiTrace.traceDestruction(this, TsapiConnCapabilities.class);
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiConnCapabilities
 * JD-Core Version:    0.5.4
 */