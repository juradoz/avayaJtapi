/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentAddress;
/*    */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ public final class LucentACDAddressImpl extends TsapiACDAddress
/*    */   implements LucentAddress
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentACDAddressImpl)
/*    */     {
/* 20 */       return this.tsDevice.equals(((LucentACDAddressImpl)obj).tsDevice);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   public LucentACDAddressImpl(TSProviderImpl tsProvider, String number)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 33 */     super(tsProvider, number);
/* 34 */     TsapiTrace.traceEntry("LucentACDAddressImpl[TSProviderImpl tsProvider, String number]", this);
/* 35 */     TsapiTrace.traceExit("LucentACDAddressImpl[TSProviderImpl tsProvider, String number]", this);
/*    */   }
/*    */ 
/*    */   LucentACDAddressImpl(TSDevice _tsDevice)
/*    */   {
/* 41 */     super(_tsDevice);
/* 42 */     TsapiTrace.traceConstruction(this, LucentACDAddressImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 48 */     super.finalize();
/* 49 */     TsapiTrace.traceDestruction(this, LucentACDAddressImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentACDAddressImpl
 * JD-Core Version:    0.5.4
 */