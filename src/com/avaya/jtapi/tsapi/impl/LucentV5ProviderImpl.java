 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentV5Provider;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.util.Vector;
 
 class LucentV5ProviderImpl extends LucentProviderImpl
   implements LucentV5Provider
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentV5ProviderImpl)
     {
       return this.tsProvider.equals(((LucentV5ProviderImpl)obj).tsProvider);
     }
 
     return false;
   }
 
   LucentV5ProviderImpl(String url, Vector<TsapiVendor> vendors)
   {
     super(url, vendors);
     TsapiTrace.traceConstruction(this, LucentV5ProviderImpl.class);
   }
 
   LucentV5ProviderImpl(TSProviderImpl _tsProvider)
   {
     super(_tsProvider);
     TsapiTrace.traceConstruction(this, LucentV5ProviderImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentV5ProviderImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV5ProviderImpl
 * JD-Core Version:    0.5.4
 */