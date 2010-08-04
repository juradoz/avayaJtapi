/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentTerminalConnection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentTerminalConnectionImpl extends TsapiTerminalConnection
/*    */   implements LucentTerminalConnection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 16 */     if (obj instanceof LucentTerminalConnectionImpl)
/*    */     {
/* 18 */       return this.tsConnection.equals(((LucentTerminalConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   LucentTerminalConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 31 */     super(_tsConnection);
/* 32 */     TsapiTrace.traceConstruction(this, LucentTerminalConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 38 */     super.finalize();
/* 39 */     TsapiTrace.traceDestruction(this, LucentTerminalConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentTerminalConnectionImpl
 * JD-Core Version:    0.5.4
 */