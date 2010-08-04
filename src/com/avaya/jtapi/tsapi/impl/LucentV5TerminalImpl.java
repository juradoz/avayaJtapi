/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5Terminal;
/*    */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ class LucentV5TerminalImpl extends LucentTerminalImpl
/*    */   implements LucentV5Terminal
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentV5TerminalImpl)
/*    */     {
/* 20 */       return this.tsDevice.equals(((LucentV5TerminalImpl)obj).tsDevice);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5TerminalImpl(LucentV5ProviderImpl _provider, String _name, boolean checkValidity)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 34 */     super(_provider, _name, checkValidity);
/* 35 */     TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5TerminalImpl(TSProviderImpl _provider, String _name)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 42 */     super(_provider, _name);
/* 43 */     TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5TerminalImpl(LucentV5ProviderImpl _provider, String _name)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 49 */     super(_provider, _name, false);
/* 50 */     TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5TerminalImpl(TSDevice _tsDevice)
/*    */   {
/* 56 */     super(_tsDevice);
/* 57 */     TsapiTrace.traceConstruction(this, LucentV5TerminalImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 63 */     super.finalize();
/* 64 */     TsapiTrace.traceDestruction(this, LucentV5TerminalImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5TerminalImpl
 * JD-Core Version:    0.5.4
 */