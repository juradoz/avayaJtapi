 package com.avaya.jtapi.tsapi.impl.events.addr;
 
 import com.avaya.jtapi.tsapi.impl.events.TsapiObserverEvent;
 import javax.telephony.Address;
 import javax.telephony.privatedata.events.PrivateAddrEv;
 
 public abstract class TsapiAddressEvent extends TsapiObserverEvent
   implements PrivateAddrEv
 {
   Address address = null;
 
   public final Address getAddress()
   {
     return this.address;
   }
 
   TsapiAddressEvent(Address _address, int _cause, int _metaCode, Object _privateData, int _eventPackage)
   {
     super(_cause, _metaCode, _privateData, _eventPackage);
     this.address = _address;
   }
 
   public TsapiAddressEvent(Address _address, int _cause, int _metaCode, Object _privateData)
   {
     this(_address, _cause, _metaCode, _privateData, 0);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.TsapiAddressEvent
 * JD-Core Version:    0.5.4
 */