 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentCallEx;
 import com.avaya.jtapi.tsapi.csta1.CSTAConnectionID;
 import com.avaya.jtapi.tsapi.impl.core.TSCall;
 import com.avaya.jtapi.tsapi.impl.core.TSProviderImpl;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 class LucentCallExImpl extends LucentCallImpl
   implements LucentCallEx
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentCallExImpl)
     {
       this.tsCall = this.tsCall.getHandOff();
       return this.tsCall.equals(((LucentCallExImpl)obj).tsCall);
     }
 
     return false;
   }
 
   LucentCallExImpl(LucentProviderImpl _provider)
   {
     super(_provider, 0);
     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
   }
 
   LucentCallExImpl(LucentProviderImpl _provider, CSTAConnectionID connID)
   {
     super(_provider, connID);
     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
   }
 
   LucentCallExImpl(LucentProviderImpl _provider, int callID)
   {
     super(_provider, callID);
     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
   }
 
   LucentCallExImpl(TSProviderImpl _provider, CSTAConnectionID connID)
   {
     super(_provider, connID);
     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
   }
 
   LucentCallExImpl(TSCall _tscall)
   {
     super(_tscall);
     TsapiTrace.traceConstruction(this, LucentCallExImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentCallExImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentCallExImpl
 * JD-Core Version:    0.5.4
 */