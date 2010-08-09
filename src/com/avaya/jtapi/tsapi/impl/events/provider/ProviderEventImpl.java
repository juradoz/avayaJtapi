 package com.avaya.jtapi.tsapi.impl.events.provider;
 
 import com.avaya.jtapi.tsapi.impl.events.TsapiListenerEvent;
 import javax.telephony.Provider;
 import javax.telephony.ProviderEvent;
 
 public class ProviderEventImpl extends TsapiListenerEvent
   implements ProviderEvent
 {
   private final Provider provider;
 
   public ProviderEventImpl(ProviderEventParams params)
   {
     super(params.getId(), params.getCause(), params.getMetaEvent(), params.getSource(), params.getPrivateData());
 
     this.provider = params.getProvider();
   }
 
   public Provider getProvider() {
     return this.provider;
   }
 
   public String toString()
   {
     return super.toString() + ";provider=" + this.provider;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.provider.ProviderEventImpl
 * JD-Core Version:    0.5.4
 */