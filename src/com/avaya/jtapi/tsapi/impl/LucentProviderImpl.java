 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentProvider;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.tsapiInterface.TsapiVendor;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import java.util.Vector;
 
 class LucentProviderImpl extends TsapiProvider
   implements LucentProvider
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentProviderImpl)
     {
       return this.tsProvider.equals(((LucentProviderImpl)obj).tsProvider);
     }
 
     return false;
   }
 
   LucentProviderImpl(String url, Vector<TsapiVendor> vendors)
   {
     super(url, vendors);
     TsapiTrace.traceEntry("LucentProviderImpl[String url, Vector<TsapiVendor> vendors]", this);
     TsapiTrace.traceExit("LucentProviderImpl[String url, Vector<TsapiVendor> vendors]", this);
   }
 
   LucentProviderImpl(TSProviderImpl _tsProvider)
   {
     super(_tsProvider);
     TsapiTrace.traceConstruction(this, LucentProviderImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentProviderImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentProviderImpl
 * JD-Core Version:    0.5.4
 */