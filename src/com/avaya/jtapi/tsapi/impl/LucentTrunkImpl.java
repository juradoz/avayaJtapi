/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentTrunk;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentTrunkImpl extends TsapiTrunkImpl
/*    */   implements LucentTrunk
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 17 */     if (obj instanceof LucentTrunkImpl)
/*    */     {
/* 19 */       return this.tsTrunk.equals(((LucentTrunkImpl)obj).tsTrunk);
/*    */     }
/*    */ 
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   LucentTrunkImpl(TSTrunk _tsTrunk)
/*    */   {
/* 30 */     super(_tsTrunk);
/* 31 */     TsapiTrace.traceConstruction(this, LucentTrunkImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 37 */     super.finalize();
/* 38 */     TsapiTrace.traceDestruction(this, LucentTrunkImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentTrunkImpl
 * JD-Core Version:    0.5.4
 */