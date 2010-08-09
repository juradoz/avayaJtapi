 package com.avaya.jtapi.tsapi.impl.events.provider;
 
 import javax.telephony.Provider;
 import javax.telephony.events.ProvObservationEndedEv;
 
 public final class TsapiProvObservationEndedEvent extends TsapiProvEvent
   implements ProvObservationEndedEv
 {
   public int getID()
   {
     return 112;
   }
 
   public TsapiProvObservationEndedEvent(Provider _provider, int _cause, Object _privateData)
   {
     super(_provider, _cause, 136, _privateData);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.TsapiProvObservationEndedEvent
 * JD-Core Version:    0.5.4
 */