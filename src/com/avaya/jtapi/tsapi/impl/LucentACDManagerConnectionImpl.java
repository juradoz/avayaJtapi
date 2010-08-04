/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentConnection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentACDManagerConnectionImpl extends TsapiACDManagerConnection
/*    */   implements LucentConnection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentACDManagerConnectionImpl)
/*    */     {
/* 20 */       return this.tsConnection.equals(((LucentACDManagerConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentACDManagerConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 31 */     super(_tsConnection);
/* 32 */     TsapiTrace.traceConstruction(this, LucentACDManagerConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 38 */     super.finalize();
/* 39 */     TsapiTrace.traceDestruction(this, LucentACDManagerConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentACDManagerConnectionImpl
 * JD-Core Version:    0.5.4
 */