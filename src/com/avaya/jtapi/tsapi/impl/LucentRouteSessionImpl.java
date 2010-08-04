/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentCallInfo;
/*    */ import com.avaya.jtapi.tsapi.LucentRouteSession;
/*    */ import com.avaya.jtapi.tsapi.V7DeviceHistoryEntry;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSRouteSession;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentRouteSessionImpl extends TsapiRouteSession
/*    */   implements LucentRouteSession, LucentCallInfo
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 19 */     if (obj instanceof LucentRouteSessionImpl)
/*    */     {
/* 21 */       return this.tsRouteSession.equals(((LucentRouteSessionImpl)obj).tsRouteSession);
/*    */     }
/*    */ 
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   public V7DeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 30 */     TsapiTrace.traceEntry("getDeviceHistory[]", this);
/* 31 */     V7DeviceHistoryEntry[] history = this.tsRouteSession.getDeviceHistory();
/* 32 */     TsapiTrace.traceExit("getDeviceHistory[]", this);
/* 33 */     return history;
/*    */   }
/*    */ 
/*    */   LucentRouteSessionImpl(TSRouteSession _tsRouteSession)
/*    */   {
/* 39 */     super(_tsRouteSession);
/* 40 */     TsapiTrace.traceConstruction(this, LucentRouteSessionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 46 */     super.finalize();
/* 47 */     TsapiTrace.traceDestruction(this, LucentRouteSessionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentRouteSessionImpl
 * JD-Core Version:    0.5.4
 */