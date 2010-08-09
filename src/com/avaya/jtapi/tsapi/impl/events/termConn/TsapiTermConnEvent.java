 package com.avaya.jtapi.tsapi.impl.events.termConn;
 
 import com.avaya.jtapi.tsapi.impl.events.call.TsapiCallEvent;
 import javax.telephony.TerminalConnection;
 
 public abstract class TsapiTermConnEvent extends TsapiCallEvent
 {
   public final TerminalConnection getTerminalConnection()
   {
     return ((TermConnEventParams)this.params).getTermConn();
   }
 
   public TsapiTermConnEvent(TermConnEventParams params, int _eventPackage)
   {
     super(params, _eventPackage);
   }
 
   public TsapiTermConnEvent(TermConnEventParams params)
   {
     this(params, 0);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TsapiTermConnEvent
 * JD-Core Version:    0.5.4
 */