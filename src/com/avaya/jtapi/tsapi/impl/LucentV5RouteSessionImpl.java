/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentRouteSession;
/*    */ import com.avaya.jtapi.tsapi.LucentV5CallInfo;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentV5RouteSessionImpl extends LucentRouteSessionImpl
/*    */   implements LucentRouteSession, LucentV5CallInfo
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentV5RouteSessionImpl)
/*    */     {
/* 20 */       return this.tsRouteSession.equals(((LucentV5RouteSessionImpl)obj).tsRouteSession);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5RouteSessionImpl(TSRouteSession _tsRouteSession)
/*    */   {
/* 31 */     super(_tsRouteSession);
/* 32 */     TsapiTrace.traceConstruction(this, LucentV5RouteSessionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 38 */     super.finalize();
/* 39 */     TsapiTrace.traceDestruction(this, LucentV5RouteSessionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5RouteSessionImpl
 * JD-Core Version:    0.5.4
 */