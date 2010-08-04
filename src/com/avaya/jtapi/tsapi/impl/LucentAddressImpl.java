/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.LucentAddress;
/*    */ import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
/*    */ import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSDevice;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ final class LucentAddressImpl extends TsapiAddress
/*    */   implements LucentAddress
/*    */ {
/*    */   public boolean equals(Object obj)
/*    */   {
/* 18 */     if (obj instanceof LucentAddressImpl)
/*    */     {
/* 20 */       return this.tsDevice.equals(((LucentAddressImpl)obj).tsDevice);
/*    */     }
/*    */ 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   LucentAddressImpl(TSDevice _tsDevice)
/*    */   {
/* 31 */     super(_tsDevice);
/* 32 */     TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
/*    */   }
/*    */ 
/*    */   LucentAddressImpl(TSProviderImpl TSProviderImpl, String number)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 39 */     super(TSProviderImpl, number);
/* 40 */     TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
/*    */   }
/*    */ 
/*    */   LucentAddressImpl(TSProviderImpl TSProviderImpl, CSTAExtendedDeviceID deviceID)
/*    */     throws TsapiInvalidArgumentException
/*    */   {
/* 46 */     super(TSProviderImpl, deviceID);
/* 47 */     TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 54 */     super.finalize();
/* 55 */     TsapiTrace.traceDestruction(this, LucentAddressImpl.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentAddressImpl
 * JD-Core Version:    0.5.4
 */