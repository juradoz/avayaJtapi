/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentV7ACDManagerAddress;
/*    */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentV7ACDManagerAddressImpl extends LucentACDManagerAddressImpl
/*    */   implements LucentV7ACDManagerAddress
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentV7ACDManagerAddressImpl)
/*    */     {
/* 20 */       return this.tsDevice.equals(((LucentV7ACDManagerAddressImpl)obj).tsDevice);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentV7ACDManagerAddressImpl(TSProviderImpl tsProvider, String number)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 32 */     super(tsProvider, number);
/* 33 */     TsapiTrace.traceConstruction(this, LucentV7ACDManagerAddressImpl.class);
/*    */   }
/*    */ 
/*    */   LucentV7ACDManagerAddressImpl(TSDevice _tsDevice)
/*    */   {
/* 39 */     super(_tsDevice);
/* 40 */     TsapiTrace.traceConstruction(this, LucentV7ACDManagerAddressImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 46 */     super.finalize();
/* 47 */     TsapiTrace.traceDestruction(this, LucentV7ACDManagerAddressImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV7ACDManagerAddressImpl
 * JD-Core Version:    0.5.4
 */