/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentAgent;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSAgent;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentAgentImpl extends TsapiAgent
/*    */   implements LucentAgent
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 17 */     if (obj instanceof LucentAgentImpl)
/*    */     {
/* 19 */       return this.tsAgent.equals(((LucentAgentImpl)obj).tsAgent);
/*    */     }
/*    */ 
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   LucentAgentImpl(TSAgent _tsAgent)
/*    */   {
/* 30 */     super(_tsAgent);
/* 31 */     TsapiTrace.traceConstruction(this, LucentAgentImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 37 */     super.finalize();
/* 38 */     TsapiTrace.traceDestruction(this, LucentAgentImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentAgentImpl
 * JD-Core Version:    0.5.4
 */