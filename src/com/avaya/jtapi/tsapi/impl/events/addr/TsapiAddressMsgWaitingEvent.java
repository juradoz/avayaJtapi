 package com.avaya.jtapi.tsapi.impl.events.addr;
 
 import com.avaya.jtapi.tsapi.ITsapiAddressMsgWaitingEvent;
 import javax.telephony.Address;
 
 public class TsapiAddressMsgWaitingEvent extends TsapiCallCtlAddressEvent
   implements ITsapiAddressMsgWaitingEvent
 {
   int mwBits = 0;
 
   public final boolean getMessageWaitingState()
   {
     return this.mwBits != 0;
   }
 
   public final int getMessageWaitingBits()
   {
     return this.mwBits;
   }
 
   public final int getID()
   {
     return 202;
   }
 
   public TsapiAddressMsgWaitingEvent(Address _device, int _mwBits, int _cause, int _metaCode, Object _privateData)
   {
     super(_device, _cause, _metaCode, _privateData);
     this.mwBits = _mwBits;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressMsgWaitingEvent
 * JD-Core Version:    0.5.4
 */