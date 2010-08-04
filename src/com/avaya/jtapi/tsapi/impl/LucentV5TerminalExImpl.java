/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV5TerminalEx;
/*    */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentV5TerminalExImpl extends LucentV5TerminalImpl
/*    */   implements LucentV5TerminalEx
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 21 */     if (obj instanceof LucentV5TerminalExImpl)
/*    */     {
/* 23 */       return this.tsDevice.equals(((LucentV5TerminalExImpl)obj).tsDevice);
/*    */     }
/*    */ 
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV5TerminalExImpl(LucentV5ProviderImpl _provider, String _name, boolean checkValidity)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 37 */     super(_provider, _name, checkValidity);
/* 38 */     TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5TerminalExImpl(TSProviderImpl _provider, String _name)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 45 */     super(_provider, _name);
/* 46 */     TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5TerminalExImpl(LucentV5ProviderImpl _provider, String _name)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 52 */     super(_provider, _name, false);
/* 53 */     TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV5TerminalExImpl(TSDevice _tsDevice)
/*    */   {
/* 59 */     super(_tsDevice);
/* 60 */     TsapiTrace.traceConstruction(this, LucentV5TerminalExImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 66 */     super.finalize();
/* 67 */     TsapiTrace.traceDestruction(this, LucentV5TerminalExImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5TerminalExImpl
 * JD-Core Version:    0.5.4
 */