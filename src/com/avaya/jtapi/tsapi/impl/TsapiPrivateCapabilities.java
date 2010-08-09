 package com.avaya.jtapi.tsapi.impl;
 
 import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
 import com.avaya.jtapi.tsapi.util.TsapiTrace;
 import javax.telephony.privatedata.capabilities.PrivateDataCapabilities;
 
 final class TsapiPrivateCapabilities
   implements PrivateDataCapabilities
 {
   TSCapabilities tsCaps = null;
 
   public boolean canSetPrivateData()
   {
     TsapiTrace.traceEntry("canSetPrivateData[]", this);
     TsapiTrace.traceExit("canSetPrivateData[]", this);
     return true;
   }
 
   public boolean canGetPrivateData()
   {
     TsapiTrace.traceEntry("canGetPrivateData[]", this);
     TsapiTrace.traceExit("canGetPrivateData[]", this);
     return true;
   }
 
   public boolean canSendPrivateData()
   {
     TsapiTrace.traceEntry("canSendPrivateData[]", this);
     TsapiTrace.traceExit("canSendPrivateData[]", this);
     return true;
   }
 
   TsapiPrivateCapabilities(TSCapabilities _tsCaps)
   {
     this.tsCaps = _tsCaps;
     TsapiTrace.traceConstruction(this, TsapiPrivateCapabilities.class);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiPrivateCapabilities
 * JD-Core Version:    0.5.4
 */