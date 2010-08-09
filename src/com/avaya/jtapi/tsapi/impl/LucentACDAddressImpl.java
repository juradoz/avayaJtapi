 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentAddress;
 import com.avaya.jtapi.tsapi.TsapiInvalidArgumentException;
 import com.avaya.jtapi.tsapi.impl.core.TSDevice;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 public final class LucentACDAddressImpl extends TsapiACDAddress
   implements LucentAddress
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentACDAddressImpl)
     {
       return this.tsDevice.equals(((LucentACDAddressImpl)obj).tsDevice);
     }
 
     return false;
   }
 
   public LucentACDAddressImpl(TSProviderImpl tsProvider, String number)
     throws TsapiInvalidArgumentException
   {
     super(tsProvider, number);
     TsapiTrace.traceEntry("LucentACDAddressImpl[TSProviderImpl tsProvider, String number]", this);
     TsapiTrace.traceExit("LucentACDAddressImpl[TSProviderImpl tsProvider, String number]", this);
   }
 
   LucentACDAddressImpl(TSDevice _tsDevice)
   {
     super(_tsDevice);
     TsapiTrace.traceConstruction(this, LucentACDAddressImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentACDAddressImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentACDAddressImpl
 * JD-Core Version:    0.5.4
 */