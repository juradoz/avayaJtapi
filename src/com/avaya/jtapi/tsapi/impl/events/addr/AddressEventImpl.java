 package com.avaya.jtapi.tsapi.impl.events.addr;
 
 import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;
 import javax.telephony.Address;
 import javax.telephony.AddressEvent;
 
 public class AddressEventImpl extends TsapiListenerEvent
   implements AddressEvent
 {
   private Address address;
 
   public AddressEventImpl(AddressEventParams addressEventParams, Address address)
   {
     super(addressEventParams.getEventId(), addressEventParams.getCause(), addressEventParams.getMetaEvent(), addressEventParams.getSource(), addressEventParams.getPrivateData());
 
     this.address = address;
   }
 
   public Address getAddress()
   {
     return this.address;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.AddressEventImpl
 * JD-Core Version:    0.5.4
 */