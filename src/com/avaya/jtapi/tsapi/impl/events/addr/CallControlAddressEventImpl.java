 package com.avaya.jtapi.tsapi.impl.events.addr;
 
 import com.avaya.jtapi.tsapi.impl.events.TsapiListenerCallControlEvent;
 import javax.telephony.Address;
 import javax.telephony.callcontrol.CallControlAddressEvent;
 import javax.telephony.callcontrol.CallControlForwarding;
 
 public class CallControlAddressEventImpl extends TsapiListenerCallControlEvent
   implements CallControlAddressEvent
 {
   CallControlForwarding[] callControlForwarding;
   boolean doNotDisturbState;
   int mwBits = 0;
   Address address;
 
   public CallControlAddressEventImpl(AddressEventParams addressEventParams, Address address)
   {
     super(addressEventParams.getEventId(), addressEventParams.getCause(), addressEventParams.getMetaEvent(), addressEventParams.getSource(), addressEventParams.getPrivateData());
 
     this.callControlForwarding = addressEventParams.getCallControlForwarding();
     this.mwBits = addressEventParams.getMwBits();
     this.doNotDisturbState = addressEventParams.isDoNotDisturbState();
     this.address = address;
   }
 
   public boolean getDoNotDisturbState()
   {
     return this.doNotDisturbState;
   }
 
   public CallControlForwarding[] getForwarding()
   {
     return this.callControlForwarding;
   }
 
   public boolean getMessageWaitingState()
   {
     return this.mwBits != 0;
   }
 
   public Address getAddress()
   {
     return this.address;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.addr.CallControlAddressEventImpl
 * JD-Core Version:    0.5.4
 */