/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentProvider;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ import java.util.Vector;
/*    */ 
/*    */ class LucentProviderImpl extends TsapiProvider
/*    */   implements LucentProvider
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 19 */     if (obj instanceof LucentProviderImpl)
/*    */     {
/* 21 */       return this.tsProvider.equals(((LucentProviderImpl)obj).tsProvider);
/*    */     }
/*    */ 
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   LucentProviderImpl(String url, Vector<TsapiVendor> vendors)
/*    */   {
/* 35 */     super(url, vendors);
/* 36 */     TsapiTrace.traceEntry("LucentProviderImpl[String url, Vector<TsapiVendor> vendors]", this);
/* 37 */     TsapiTrace.traceExit("LucentProviderImpl[String url, Vector<TsapiVendor> vendors]", this);
/*    */   }
/*    */ 
/*    */   LucentProviderImpl(TSProviderImpl _tsProvider)
/*    */   {
/* 47 */     super(_tsProvider);
/* 48 */     TsapiTrace.traceConstruction(this, LucentProviderImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 54 */     super.finalize();
/* 55 */     TsapiTrace.traceDestruction(this, LucentProviderImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentProviderImpl
 * JD-Core Version:    0.5.4
 */