/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentCallEx;
/*    */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentCallExImpl extends LucentCallImpl
/*    */   implements LucentCallEx
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 17 */     if (obj instanceof LucentCallExImpl)
/*    */     {
/* 19 */       this.tsCall = this.tsCall.getHandOff();
/* 20 */       return this.tsCall.equals(((LucentCallExImpl)obj).tsCall);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentCallExImpl(LucentProviderImpl _provider)
/*    */   {
/* 31 */     super(_provider, 0);
/* 32 */     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
/*    */   }
/*    */ 
/*    */   LucentCallExImpl(LucentProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 38 */     super(_provider, connID);
/* 39 */     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
/*    */   }
/*    */ 
/*    */   LucentCallExImpl(LucentProviderImpl _provider, int callID)
/*    */   {
/* 45 */     super(_provider, callID);
/* 46 */     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
/*    */   }
/*    */ 
/*    */   LucentCallExImpl(TSProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 52 */     super(_provider, connID);
/* 53 */     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
/*    */   }
/*    */ 
/*    */   LucentCallExImpl(TSCall _tscall)
/*    */   {
/* 59 */     super(_tscall);
/* 60 */     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 66 */     super.finalize();
/* 67 */     TsapiTrace.traceDestruction(this, LucentCallExImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentCallExImpl
 * JD-Core Version:    0.5.4
 */