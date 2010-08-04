/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentACDManagerAddress;
/*    */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ public class LucentACDManagerAddressImpl extends TsapiACDManagerAddress
/*    */   implements LucentACDManagerAddress
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentACDManagerAddressImpl)
/*    */     {
/* 20 */       return this.tsDevice.equals(((LucentACDManagerAddressImpl)obj).tsDevice);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   public LucentACDManagerAddressImpl(TSProviderImpl tsProvider, String number)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 32 */     super(tsProvider, number);
/* 33 */     TsapiTrace.traceEntry("LucentACDManagerAddressImpl[TSProviderImpl tsProvider, String number]", this);
/* 34 */     TsapiTrace.traceExit("LucentACDManagerAddressImpl[TSProviderImpl tsProvider, String number]", this);
/*    */   }
/*    */ 
/*    */   LucentACDManagerAddressImpl(TSDevice _tsDevice)
/*    */   {
/* 40 */     super(_tsDevice);
/* 41 */     TsapiTrace.traceConstruction(this, LucentACDManagerAddressImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 47 */     super.finalize();
/* 48 */     TsapiTrace.traceDestruction(this, LucentACDManagerAddressImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentACDManagerAddressImpl
 * JD-Core Version:    0.5.4
 */