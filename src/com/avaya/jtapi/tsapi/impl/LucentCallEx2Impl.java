/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentCallEx2;
/*    */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentCallEx2Impl extends LucentCallExImpl
/*    */   implements LucentCallEx2
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentCallEx2Impl)
/*    */     {
/* 20 */       this.tsCall = this.tsCall.getHandOff();
/* 21 */       return this.tsCall.equals(((LucentCallEx2Impl)obj).tsCall);
/*    */     }
/*    */ 
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   LucentCallEx2Impl(LucentProviderImpl _provider)
/*    */   {
/* 32 */     super(_provider, 0);
/* 33 */     TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
/*    */   }
/*    */ 
/*    */   LucentCallEx2Impl(LucentProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 39 */     super(_provider, connID);
/* 40 */     TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
/*    */   }
/*    */ 
/*    */   LucentCallEx2Impl(LucentProviderImpl _provider, int callID)
/*    */   {
/* 46 */     super(_provider, callID);
/* 47 */     TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
/*    */   }
/*    */ 
/*    */   LucentCallEx2Impl(TSProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 53 */     super(_provider, connID);
/* 54 */     TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
/*    */   }
/*    */ 
/*    */   LucentCallEx2Impl(TSCall _tscall)
/*    */   {
/* 60 */     super(_tscall);
/* 61 */     TsapiTrace.traceConstruction(this, LucentCallEx2Impl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 67 */     super.finalize();
/* 68 */     TsapiTrace.traceDestruction(this, LucentCallEx2Impl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentCallEx2Impl
 * JD-Core Version:    0.5.4
 */