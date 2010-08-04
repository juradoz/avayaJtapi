/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5Connection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentV5ACDManagerConnectionImpl extends LucentACDManagerConnectionImpl
/*    */   implements LucentV5Connection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 17 */     if (obj instanceof LucentV5ACDManagerConnectionImpl)
/*    */     {
/* 19 */       return this.tsConnection.equals(((LucentV5ACDManagerConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5ACDManagerConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 30 */     super(_tsConnection);
/* 31 */     TsapiTrace.traceConstruction(this, LucentV5ACDManagerConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 37 */     super.finalize();
/* 38 */     TsapiTrace.traceDestruction(this, LucentV5ACDManagerConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5ACDManagerConnectionImpl
 * JD-Core Version:    0.5.4
 */