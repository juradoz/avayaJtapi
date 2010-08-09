 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.csta1.CSTAEscapeSvcConfEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 
 class EscapeConfHandler
   implements ConfHandler
 {
   TSProviderImpl prov;
   ConfHandler extraHandler;
   protected Object privateData;
 
   EscapeConfHandler(TSProviderImpl _prov, ConfHandler _extraHandler)
   {
     this.prov = _prov;
     this.extraHandler = _extraHandler;
   }
 
   public void handleConf(CSTAEvent event) {
     Object privData = null;
     try {
       if ((event == null) || (!(event.getEvent() instanceof CSTAEscapeSvcConfEvent)))
       {
         return;
       }
 
       privData = event.getPrivData();
       this.prov.replyPriv = privData;
       this.privateData = privData;
     } finally {
       if (this.extraHandler != null)
         this.extraHandler.handleConf(event);
     }
   }
 
   public Object getPrivateData()
   {
     return this.privateData;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.EscapeConfHandler
 * JD-Core Version:    0.5.4
 */