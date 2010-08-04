/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5TerminalConnection;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentV5TerminalConnectionImpl extends LucentTerminalConnectionImpl
/*    */   implements LucentV5TerminalConnection
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 17 */     if (obj instanceof LucentV5TerminalConnectionImpl)
/*    */     {
/* 19 */       return this.tsConnection.equals(((LucentV5TerminalConnectionImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5TerminalConnectionImpl(TSConnection _tsConnection)
/*    */   {
/* 32 */     super(_tsConnection);
/* 33 */     TsapiTrace.traceConstruction(this, LucentV5TerminalConnectionImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 39 */     super.finalize();
/* 40 */     TsapiTrace.traceDestruction(this, LucentV5TerminalConnectionImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5TerminalConnectionImpl
 * JD-Core Version:    0.5.4
 */