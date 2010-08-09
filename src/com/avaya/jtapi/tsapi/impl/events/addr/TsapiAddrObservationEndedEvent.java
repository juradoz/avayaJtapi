 package com.avaya.jtapi.tsapi.impl.events.addr;
 
 import javax.telephony.Address;
 import javax.telephony.events.AddrObservationEndedEv;
 
 public final class TsapiAddrObservationEndedEvent extends TsapiAddressEvent
   implements AddrObservationEndedEv
 {
   public int getID()
   {
     return 100;
   }
 
   public TsapiAddrObservationEndedEvent(Address _address, int _cause, Object _privateData)
   {
     super(_address, _cause, 136, _privateData);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddrObservationEndedEvent
 * JD-Core Version:    0.5.4
 */