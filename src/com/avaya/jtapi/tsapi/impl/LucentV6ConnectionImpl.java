 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentV6Connection;
 import com.avaya.jtapi.tsapi.impl.core.TSConnection;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 final class LucentV6ConnectionImpl extends LucentV5ConnectionImpl
   implements LucentV6Connection
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentV6ConnectionImpl)
     {
       return this.tsConnection.equals(((LucentV6ConnectionImpl)obj).tsConnection);
     }
 
     return false;
   }
 
   LucentV6ConnectionImpl(TSConnection _tsConnection)
   {
     super(_tsConnection);
     TsapiTrace.traceConstruction(this, LucentV5ConnectionImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentV5ConnectionImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentV6ConnectionImpl
 * JD-Core Version:    0.5.4
 */