 package com.avaya.jtapi.tsapi.impl.events.provider;
 
 import com.avaya.jtapi.tsapi.ITsapiProviderTsapiInitializingEvent;
 import com.avaya.jtapi.tsapi.impl.events.TsapiPrivateStateEvent;
 
 public final class TsapiProviderTsapiInitializingEvent extends TsapiPrivateStateEvent
   implements ITsapiProviderTsapiInitializingEvent
 {
   public TsapiProviderTsapiInitializingEvent()
   {
     super(1);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.TsapiProviderTsapiInitializingEvent
 * JD-Core Version:    0.5.4
 */