/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV6Connection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentV6ConnectionImpl extends LucentV5ConnectionImpl
/*    */   implements LucentV6Connection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 19 */     if (obj instanceof LucentV6ConnectionImpl)
/*    */     {
/* 21 */       return this.tsConnection.equals(((LucentV6ConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV6ConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 33 */     super(_tsConnection);
/* 34 */     TsapiTrace.traceConstruction(this, LucentV5ConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 40 */     super.finalize();
/* 41 */     TsapiTrace.traceDestruction(this, LucentV5ConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV6ConnectionImpl
 * JD-Core Version:    0.5.4
 */