/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentTerminal;
/*    */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ public class LucentTerminalImpl extends TsapiTerminal
/*    */   implements LucentTerminal
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentTerminalImpl)
/*    */     {
/* 20 */       return this.tsDevice.equals(((LucentTerminalImpl)obj).tsDevice);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentTerminalImpl(LucentProviderImpl _provider, String _name, boolean checkValidity)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 34 */     super(_provider, _name, checkValidity);
/* 35 */     TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
/*    */   }
/*    */ 
/*    */   public LucentTerminalImpl(TSProviderImpl _provider, String _name)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 42 */     super(_provider, _name);
/* 43 */     TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
/*    */   }
/*    */ 
/*    */   LucentTerminalImpl(LucentProviderImpl _provider, String _name)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 49 */     super(_provider, _name, false);
/* 50 */     TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
/*    */   }
/*    */ 
/*    */   LucentTerminalImpl(TSDevice _tsDevice)
/*    */   {
/* 56 */     super(_tsDevice);
/* 57 */     TsapiTrace.traceConstruction(this, LucentTerminalImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 63 */     super.finalize();
/* 64 */     TsapiTrace.traceDestruction(this, LucentTerminalImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentTerminalImpl
 * JD-Core Version:    0.5.4
 */