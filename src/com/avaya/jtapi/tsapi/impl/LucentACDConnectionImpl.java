/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentConnection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentACDConnectionImpl extends TsapiACDConnection
/*    */   implements LucentConnection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 16 */     if (obj instanceof LucentACDConnectionImpl)
/*    */     {
/* 18 */       return this.tsConnection.equals(((LucentACDConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   LucentACDConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 29 */     super(_tsConnection);
/* 30 */     TsapiTrace.traceConstruction(this, LucentACDConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 36 */     super.finalize();
/* 37 */     TsapiTrace.traceDestruction(this, LucentACDConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentACDConnectionImpl
 * JD-Core Version:    0.5.4
 */