 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentConnection;
 import com.avaya.jtapi.tsapi.impl.core.TSConnection;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 class LucentACDConnectionImpl extends TsapiACDConnection
   implements LucentConnection
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentACDConnectionImpl)
     {
       return this.tsConnection.equals(((LucentACDConnectionImpl)obj).tsConnection);
     }
 
     return false;
   }
 
   LucentACDConnectionImpl(TSConnection _tsConnection)
   {
     super(_tsConnection);
     TsapiTrace.traceConstruction(this, LucentACDConnectionImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentACDConnectionImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentACDConnectionImpl
 * JD-Core Version:    0.5.4
 */