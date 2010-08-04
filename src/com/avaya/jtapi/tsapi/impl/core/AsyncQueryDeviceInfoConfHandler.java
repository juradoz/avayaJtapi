/*      */ package com.avaya.jtapi.tsapi.impl.core;
/*      */ 
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.CSTAQueryDeviceInfoConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentQueryDeviceInfoConfEvent;
/*      */ import com.avaya.jtapi.tsapi.csta1.LucentV5QueryDeviceInfoConfEvent;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.ConfHandler;
/*      */ import com.avaya.jtapi.tsapi.tsapiInterface.HandleConfOnCurrentThread;
/*      */ 
/*      */ final class AsyncQueryDeviceInfoConfHandler
/*      */   implements ConfHandler, HandleConfOnCurrentThread
/*      */ {
/*      */   TSDevice device;
/*      */ 
/*      */   AsyncQueryDeviceInfoConfHandler(TSDevice _device)
/*      */   {
/* 4715 */     this.device = _device;
/*      */   }
/*      */ 
/*      */   public void handleConf(CSTAEvent event)
/*      */   {
/*      */     try
/*      */     {
/* 4722 */       if ((event == null) || (!(event.getEvent() instanceof CSTAQueryDeviceInfoConfEvent)))
/*      */       {
/* 4726 */         this.device.setIsATerminal(false);
/* 4727 */         this.device.setIsExternal(true);
/* 4728 */         return;
/*      */       }
/* 4730 */       CSTAQueryDeviceInfoConfEvent devInfoConf = (CSTAQueryDeviceInfoConfEvent)event.getEvent();
/*      */ 
/* 4732 */       if ((devInfoConf.getDeviceType() != 0) && (devInfoConf.getDeviceType() != 2) && (devInfoConf.getDeviceType() != 5) && (devInfoConf.getDeviceType() != 18) && (devInfoConf.getDeviceType() != 1)) if (devInfoConf.getDeviceType() != 255)
/*      */         {
/* 4743 */           this.device.setIsATerminal(false);
/*      */ 
/* 4745 */           Object replyPriv = event.getPrivData();
/*      */ 
/* 4747 */           if (replyPriv instanceof LucentQueryDeviceInfoConfEvent)
/*      */           {
/* 4749 */             if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 0)
/*      */             {
/* 4751 */               this.device.setDeviceType((short) 1);
/*      */             }
/* 4753 */             else if (((LucentQueryDeviceInfoConfEvent)replyPriv).getExtensionClass() == 1)
/*      */             {
/* 4755 */               this.device.setDeviceType((short) 2);
/*      */             }
/*      */ 
/* 4758 */             if (replyPriv instanceof LucentV5QueryDeviceInfoConfEvent)
/*      */             {
/* 4761 */               this.device.setAssociatedDevice(((LucentV5QueryDeviceInfoConfEvent)replyPriv).getAssociatedDevice());
/* 4762 */               this.device.setAssociatedClass(((LucentV5QueryDeviceInfoConfEvent)replyPriv).getAssociatedClass());
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4771 */       this.device.setIsATerminal(false);
/* 4772 */       this.device.setIsExternal(true);
/*      */     }
/*      */     finally
/*      */     {
/* 4776 */       this.device.notifyAsyncInitializationComplete();
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.AsyncQueryDeviceInfoConfHandler
 * JD-Core Version:    0.5.4
 */