 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;
 
 final class PriorityEscapeConfHandler extends EscapeConfHandler
   implements HandleConfOnCurrentThread
 {
   PriorityEscapeConfHandler(TSProviderImpl _prov, ConfHandler _extraHandler)
   {
     super(_prov, _extraHandler);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.PriorityEscapeConfHandler
 * JD-Core Version:    0.5.4
 */