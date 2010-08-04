/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV7CallInfo;
/*    */ import com.avaya.jtapi.tsapi.LucentV7RouteSession;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentV7RouteSessionImpl extends LucentRouteSessionImpl
/*    */   implements LucentV7RouteSession, LucentV7CallInfo
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 19 */     if (obj instanceof LucentV7RouteSessionImpl)
/*    */     {
/* 21 */       return this.tsRouteSession.equals(((LucentV7RouteSessionImpl)obj).tsRouteSession);
/*    */     }
/*    */ 
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV7RouteSessionImpl(TSRouteSession _tsRouteSession)
/*    */   {
/* 32 */     super(_tsRouteSession);
/* 33 */     TsapiTrace.traceConstruction(this, LucentV7RouteSessionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 39 */     super.finalize();
/* 40 */     TsapiTrace.traceDestruction(this, LucentV7RouteSessionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV7RouteSessionImpl
 * JD-Core Version:    0.5.4
 */