 package com.avaya.jtapi.tsapi.impl.core;
 
 import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
 import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceInfoConfEvent;
 import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
 import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
 import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;
 
 final class AsyncQueryDeviceInfoConfHandler
   implements ConfHandler, HandleConfOnCurrentThread
 {
   TSDevice device;
 
   AsyncQueryDeviceInfoConfHandler(TSDevice _device)
   {
     this.device = _device;
   }
 
   public void handleConf(CSTAEvent event)
   {
     try
     {
       if ((event == null) || (!(event.getEvent() instanceof CSTAQueryDeviceInfoConfEvent)))
       {
         this.device.setIsATerminal(false);
         this.device.setIsExternal(true);
         return;
       }
       CSTAQueryDeviceInfoConfEvent devInfoConf = (CSTAQueryDeviceInfoConfEvent)event.getEvent();
 
       if ((devInfoConf.getDeviceType() != 0) && (devInfoConf.getDeviceType() != 2) && (devInfoConf.getDeviceType() != 5) && (devInfoConf.getDeviceType() != 18) && (devInfoConf.getDeviceType() != 1)) if (devInfoConf.getDeviceType() != 255)
         {
           this.device.setIsATerminal(false);
 
           Object replyPriv = event.getPrivData();
 
           if (replyPriv instanceof LucentQueryDeviceInfoConfEvent)
           {
             if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 0)
             {
               this.device.setDeviceType((short) 1);
             }
             else if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 1)
             {
               this.device.setDeviceType((short) 2);
             }
 
             if (replyPriv instanceof LucentV5QueryDeviceInfoConfEvent)
             {
               this.device.setAssociatedDevice(((LucentV5QueryDeviceInfoConfEvent)replyPriv).getAssociatedDevice());
               this.device.setAssociatedClass(((LucentV5QueryDeviceInfoConfEvent)replyPriv).getAssociatedClass());
             }
           }
         }
 
 
     }
     catch (Exception e)
     {
       this.device.setIsATerminal(false);
       this.device.setIsExternal(true);
     }
     finally
     {
       this.device.notifyAsyncInitializationComplete();
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.AsyncQueryDeviceInfoConfHandler
 * JD-Core Version:    0.5.4
 */