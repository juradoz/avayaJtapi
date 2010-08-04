/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5TerminalConnectionEx;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ public final class LucentV5TerminalConnectionExImpl extends LucentV5TerminalConnectionImpl
/*    */   implements LucentV5TerminalConnectionEx
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 13 */     if (obj instanceof LucentV5TerminalConnectionExImpl)
/*    */     {
/* 15 */       return this.tsConnection.equals(((LucentV5TerminalConnectionExImpl)obj).tsConnection);
/*    */     }
/*    */ 
/* 19 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5TerminalConnectionExImpl(TSConnection _tsConnection)
/*    */   {
/* 28 */     super(_tsConnection);
/* 29 */     TsapiTrace.traceConstruction(this, LucentV5TerminalConnectionExImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 35 */     super.finalize();
/* 36 */     TsapiTrace.traceDestruction(this, LucentV5TerminalConnectionExImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5TerminalConnectionExImpl
 * JD-Core Version:    0.5.4
 */