/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentConnection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentConnectionImpl extends TsapiConnection
/*    */   implements LucentConnection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 16 */     if (obj instanceof LucentConnectionImpl)
/*    */     {
/* 18 */       return this.tsConnection.equals(((LucentConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   LucentConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 30 */     super(_tsConnection);
/* 31 */     TsapiTrace.traceConstruction(this, LucentConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 37 */     super.finalize();
/* 38 */     TsapiTrace.traceDestruction(this, LucentConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentConnectionImpl
 * JD-Core Version:    0.5.4
 */