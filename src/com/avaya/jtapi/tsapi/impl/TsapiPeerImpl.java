/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.ITsapiPeer;
/*    */ import com.avaya.jtapi.tsapi.impl.core.AbstractTsapiPeer;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ import javax.telephony.Provider;
/*    */ 
/*    */ public class TsapiPeerImpl extends AbstractTsapiPeer
/*    */   implements ITsapiPeer
/*    */ {
/*    */   public TsapiPeerImpl()
/*    */   {
/* 19 */     TsapiTrace.traceConstruction(this, TsapiPeerImpl.class);
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 23 */     TsapiTrace.traceEntry("getName[]", this);
/* 24 */     String name = super.getClass().getName();
/* 25 */     TsapiTrace.traceExit("getName[]", this);
/* 26 */     return name;
/*    */   }
/*    */ 
/*    */   public final Provider getProvider(String providerString) {
/* 30 */     TsapiTrace.traceEntry("getProvider[String providerString]", this);
/*    */ 
/* 32 */     TsapiProvider tsapiProvider = new TsapiProvider(providerString, this.vendors);
/* 33 */     this.vendors = null;
/*    */     Provider provider;
/* 34 */     if (tsapiProvider.tsProvider.isLucent()) {
/* 35 */       provider = (Provider)TsapiCreateObject.getTsapiObject(tsapiProvider.tsProvider, false);
/*    */     }
/*    */     else {
/* 38 */       provider = tsapiProvider;
/*    */     }
/* 40 */     TsapiTrace.traceExit("getProvider[String providerString]", this);
/* 41 */     return provider;
/*    */   }
/*    */ 
/*    */   public void addVendor(String name, String versions) {
/* 45 */     TsapiTrace.traceEntry("addVendor[String name, String versions]", this);
/* 46 */     super.addVendor(name, versions);
/* 47 */     TsapiTrace.traceExit("addVendor[String name, String versions]", this);
/*    */   }
/*    */ 
/*    */   public String[] getServices() {
/* 51 */     TsapiTrace.traceEntry("getServices[]", this);
/* 52 */     String[] services = super.getServices();
/* 53 */     TsapiTrace.traceExit("getServices[]", this);
/* 54 */     return services;
/*    */   }
/*    */ 
/*    */   protected void finalize() throws Throwable
/*    */   {
/* 59 */     super.finalize();
/* 60 */     TsapiTrace.traceDestruction(this, TsapiPeerImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiPeerImpl
 * JD-Core Version:    0.5.4
 */