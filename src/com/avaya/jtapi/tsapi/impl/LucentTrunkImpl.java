 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.LucentTrunk;
 import com.avaya.jtapi.tsapi.impl.core.TSTrunk;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 final class LucentTrunkImpl extends TsapiTrunkImpl
   implements LucentTrunk
 {
   public boolean equals(Object obj)
   {
     if (obj instanceof LucentTrunkImpl)
     {
       return this.tsTrunk.equals(((LucentTrunkImpl)obj).tsTrunk);
     }
 
     return false;
   }
 
   LucentTrunkImpl(TSTrunk _tsTrunk)
   {
     super(_tsTrunk);
     TsapiTrace.traceConstruction(this, LucentTrunkImpl.class);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, LucentTrunkImpl.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.LucentTrunkImpl
 * JD-Core Version:    0.5.4
 */