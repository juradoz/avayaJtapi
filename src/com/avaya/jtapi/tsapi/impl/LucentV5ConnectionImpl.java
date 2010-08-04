/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5Connection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentV5ConnectionImpl extends LucentConnectionImpl
/*    */   implements LucentV5Connection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 16 */     if (obj instanceof LucentV5ConnectionImpl)
/*    */     {
/* 18 */       return this.tsConnection.equals(((LucentV5ConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5ConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 30 */     super(_tsConnection);
/* 31 */     TsapiTrace.traceConstruction(this, LucentV5ConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 37 */     super.finalize();
/* 38 */     TsapiTrace.traceDestruction(this, LucentV5ConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5ConnectionImpl
 * JD-Core Version:    0.5.4
 */