 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentConnection;
 import com.avaya.jtapi.tsapi.impl.core.TSConnection;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 class LucentACDManagerConnectionImpl extends TsapiACDManagerConnection
   implements LucentConnection
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentACDManagerConnectionImpl)
     {
       return this.tsConnection.equals(((LucentACDManagerConnectionImpl)obj).tsConnection);
     }
 
     return false;
   }
 
   LucentACDManagerConnectionImpl(TSConnection _tsConnection)
   {
     super(_tsConnection);
     TsapiTrace.traceConstruction(this, LucentACDManagerConnectionImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentACDManagerConnectionImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentACDManagerConnectionImpl
 * JD-Core Version:    0.5.4
 */