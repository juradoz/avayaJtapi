 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentAddress;
 import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
 import com.avaya.jtapi.tsapi.csta1.CSTAExtendedDeviceID;
 import com.avaya.jtapi.tsapi.impl.core.TSDevice;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 final class LucentAddressImpl extends TsapiAddress
   implements LucentAddress
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentAddressImpl)
     {
       return this.tsDevice.equals(((LucentAddressImpl)obj).tsDevice);
     }
 
     return false;
   }
 
   LucentAddressImpl(TSDevice _tsDevice)
   {
     super(_tsDevice);
     TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
   }
 
   LucentAddressImpl(TSProviderImpl TSProviderImpl, String number)
     throws TsapiInvalidArgumentException
   {
     super(TSProviderImpl, number);
     TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
   }
 
   LucentAddressImpl(TSProviderImpl TSProviderImpl, CSTAExtendedDeviceID deviceID)
     throws TsapiInvalidArgumentException
   {
     super(TSProviderImpl, deviceID);
     TsapiTrace.traceConstruction(this, LucentAddressImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentAddressImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentAddressImpl
 * JD-Core Version:    0.5.4
 */