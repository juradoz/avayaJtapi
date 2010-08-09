 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.impl.monitor.TsapiCallMonitor;
 
 final class CallbackAndType
 {
   TsapiCallMonitor callback;
   DevWithType devWithType;
 
   CallbackAndType(TsapiCallMonitor _callback, DevWithType _devWithType)
   {
     this.callback = _callback;
     this.devWithType = _devWithType;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.CallbackAndType
 * JD-Core Version:    0.5.4
 */