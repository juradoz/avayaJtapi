 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 
 public final class TsapiCallControlForwarding
 {
   String destAddress;
   int type;
   int whichCalls;
 
   public String getDestinationAddress()
   {
     TsapiTrace.traceEntry("getDestinationAddress[]", this);
     TsapiTrace.traceExit("getDestinationAddress[]", this);
     return this.destAddress;
   }
 
   public int getType()
   {
     TsapiTrace.traceEntry("getType[]", this);
     TsapiTrace.traceExit("getType[]", this);
     return this.type;
   }
 
   public int getFilter()
   {
     TsapiTrace.traceEntry("getFilter[]", this);
     TsapiTrace.traceExit("getFilter[]", this);
     return this.whichCalls;
   }
 
   public TsapiCallControlForwarding(String _destAddress, int _type)
   {
     this.destAddress = _destAddress;
     this.type = _type;
     this.whichCalls = 1;
     TsapiTrace.traceConstruction(this, TsapiCallControlForwarding.class);
   }
 
   public TsapiCallControlForwarding(String _destAddress, int _type, boolean internalCalls)
   {
     this.destAddress = _destAddress;
     this.type = _type;
     if (internalCalls)
     {
       this.whichCalls = 2;
     }
     else
     {
       this.whichCalls = 3;
     }
     TsapiTrace.traceConstruction(this, TsapiCallControlForwarding.class);
   }
 
   public boolean equals(TsapiCallControlForwarding other)
   {
     return (this.destAddress == other.destAddress) && (this.type == other.type) && (this.whichCalls == other.whichCalls);
   }
 
   protected void finalize()
     throws Throwable
   {
     super.finalize();
     TsapiTrace.traceDestruction(this, TsapiCallControlForwarding.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiCallControlForwarding
 * JD-Core Version:    0.5.4
 */