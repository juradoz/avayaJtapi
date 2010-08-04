/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5Call;
/*    */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentV5CallImpl extends LucentCallEx2Impl
/*    */   implements LucentV5Call
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 17 */     if (obj instanceof LucentV5CallImpl)
/*    */     {
/* 19 */       this.tsCall = this.tsCall.getHandOff();
/* 20 */       return this.tsCall.equals(((LucentV5CallImpl)obj).tsCall);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5CallImpl(LucentV5ProviderImpl _provider)
/*    */   {
/* 31 */     super(_provider, 0);
/* 32 */     TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5CallImpl(LucentV5ProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 38 */     super(_provider, connID);
/* 39 */     TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5CallImpl(LucentV5ProviderImpl _provider, int callID)
/*    */   {
/* 45 */     super(_provider, callID);
/* 46 */     TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5CallImpl(TSProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 52 */     super(_provider, connID);
/* 53 */     TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5CallImpl(TSCall _tscall)
/*    */   {
/* 59 */     super(_tscall);
/* 60 */     TsapiTrace.traceConstruction(this, LucentV5CallImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 66 */     super.finalize();
/* 67 */     TsapiTrace.traceDestruction(this, LucentV5CallImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5CallImpl
 * JD-Core Version:    0.5.4
 */