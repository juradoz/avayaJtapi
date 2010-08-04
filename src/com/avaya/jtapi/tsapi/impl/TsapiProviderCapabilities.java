/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ import javax.telephony.callcenter.capabilities.CallCenterProviderCapabilities;
/*    */ import javax.telephony.capabilities.ProviderCapabilities;
/*    */ 
/*    */ public final class TsapiProviderCapabilities
/*    */   implements ProviderCapabilities, CallCenterProviderCapabilities
/*    */ {
/* 17 */   private TSCapabilities tsCaps = null;
/*    */ 
/*    */   public boolean isObservable()
/*    */   {
/* 23 */     TsapiTrace.traceEntry("isObservable[]", this);
/* 24 */     TsapiTrace.traceExit("isObservable[]", this);
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean canGetRouteableAddresses()
/*    */   {
/* 31 */     TsapiTrace.traceEntry("canGetRouteableAddresses[]", this);
/* 32 */     TsapiTrace.traceExit("canGetRouteableAddresses[]", this);
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean canGetACDAddresses()
/*    */   {
/* 38 */     TsapiTrace.traceEntry("canGetACDAddresses[]", this);
/* 39 */     boolean can = this.tsCaps.isLucent();
/* 40 */     TsapiTrace.traceExit("canGetACDAddresses[]", this);
/* 41 */     return can;
/*    */   }
/*    */ 
/*    */   public boolean canGetACDManagerAddresses()
/*    */   {
/* 46 */     TsapiTrace.traceEntry("canGetACDManagerAddresses[]", this);
/* 47 */     boolean can = this.tsCaps.isLucent();
/* 48 */     TsapiTrace.traceExit("canGetACDManagerAddresses[]", this);
/* 49 */     return can;
/*    */   }
/*    */ 
/*    */   public TsapiProviderCapabilities(TSCapabilities _tsCaps)
/*    */   {
/* 55 */     this.tsCaps = _tsCaps;
/* 56 */     TsapiTrace.traceConstruction(this, TsapiProviderCapabilities.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 62 */     super.finalize();
/* 63 */     TsapiTrace.traceDestruction(this, TsapiProviderCapabilities.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiProviderCapabilities
 * JD-Core Version:    0.5.4
 */