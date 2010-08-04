/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentCall;
/*    */ import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSCall;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ public class LucentCallImpl extends TsapiCall
/*    */   implements LucentCall
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 17 */     if (obj instanceof LucentCallImpl)
/*    */     {
/* 19 */       this.tsCall = this.tsCall.getHandOff();
/* 20 */       return this.tsCall.equals(((LucentCallImpl)obj).tsCall);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentCallImpl(LucentProviderImpl _provider)
/*    */   {
/* 31 */     super(_provider, 0);
/* 32 */     TsapiTrace.traceConstruction(this, LucentCallImpl.class);
/*    */   }
/*    */ 
/*    */   LucentCallImpl(LucentProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 38 */     super(_provider, connID);
/* 39 */     TsapiTrace.traceConstruction(this, LucentCallImpl.class);
/*    */   }
/*    */ 
/*    */   LucentCallImpl(LucentProviderImpl _provider, int callID)
/*    */   {
/* 45 */     super(_provider, callID);
/* 46 */     TsapiTrace.traceConstruction(this, LucentCallImpl.class);
/*    */   }
/*    */ 
/*    */   public LucentCallImpl(TSProviderImpl _provider, CSTAConnectionID connID)
/*    */   {
/* 52 */     super(_provider, connID);
/* 53 */     TsapiTrace.traceConstruction(this, LucentCallImpl.class);
/*    */   }
/*    */ 
/*    */   LucentCallImpl(TSCall _tscall)
/*    */   {
/* 59 */     super(_tscall);
/* 60 */     TsapiTrace.traceConstruction(this, LucentCallImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 66 */     super.finalize();
/* 67 */     TsapiTrace.traceDestruction(this, LucentCallImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentCallImpl
 * JD-Core Version:    0.5.4
 */